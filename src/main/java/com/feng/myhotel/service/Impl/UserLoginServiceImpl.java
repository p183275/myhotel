package com.feng.myhotel.service.Impl;

import cn.hutool.json.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.feng.myhotel.config.OSSProperties;
import com.feng.myhotel.entities.constant.ExceptionConstant;
import com.feng.myhotel.entities.constant.LoginConstant;
import com.feng.myhotel.entities.po.UserPO;
import com.feng.myhotel.entities.result.CommonResult;
import com.feng.myhotel.entities.vo.UserRegisterVO;
import com.feng.myhotel.mapper.UserPOMapper;
import com.feng.myhotel.service.UserLoginService;
import com.feng.myhotel.utils.HTTPUtils;
import com.feng.myhotel.utils.JWTUtils;
import com.feng.myhotel.utils.OSSUtils;
import com.feng.myhotel.utils.RegisterUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author : pcf
 * @date : 2021/11/11 9:19
 */
@Service
@Slf4j
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserPOMapper userPOMapper;
    @Autowired
    private OSSProperties ossProperties;

    /**
     * 用户使用电话号码登录
     * @param map 封装电话号码的信息
     * @return token
     */
    @Override
    public Map<String, String> userLoginByPhone(Map<String, String> map) {

        // 拿到电话号码
        String phoneNumber = map.get(LoginConstant.VARIABLE_PHONE_NUMBER);

        // 拿到redis中缓存的验证码，并判断是否为空
        String checkCodeFromRedis = (String) redisTemplate.opsForValue()
                .get(phoneNumber + LoginConstant.VALUE_CREATE_CHECK_CODE);
        if (checkCodeFromRedis == null)
            throw new RuntimeException(ExceptionConstant.CHECK_CODE_INPUT_ERROR_EXCEPTION);

        // 从map中拿到验证码，并判断是否为空
        String checkCode = map.get(LoginConstant.VARIABLE_CHECK_CODE);
        if (checkCode == null)
            throw new RuntimeException(ExceptionConstant.CHECK_CODE_IS_NOT_NULL);

        // 判断，不通过返回验证码错误
        if (!checkCode.equals(checkCodeFromRedis))
            throw new RuntimeException(ExceptionConstant.CHECK_CODE_INPUT_ERROR_EXCEPTION);

        // 用电话号码查询数据库
        UserPO userPO = userPOMapper.selectByPhoneNumber(phoneNumber);

        // 判断查出来的数据,不通过返回无绑定角色异常信息
        if (userPO == null)
            throw new RuntimeException(ExceptionConstant.PHONE_INPUT_ERROR_EXCEPTION);

        // json工具
        JSONObject json = new JSONObject();

        // 将账号密码放入map
        map.put(LoginConstant.VARIABLE_LOGIN_ACCOUNT, userPO.getLoginAccount());
        map.put(LoginConstant.VARIABLE_PASSWORD, userPO.getPassword());

        // 将map转为json格式
        json.putAll(map);

        // 调用HTTPUtils方法
        String result = HTTPUtils.post(json, LoginConstant.VARIABLE_BASE_URL + "/user/login");

        // 拿到token
        Map<String, String> tokenMap = HTTPUtils.getTokenFromResult(result);

        return tokenMap;
    }

    /**
     * 发送短信拿到验证码
     * @param map 电话号码键值对
     */
    @Override
    public void userGetCheckCode(Map<String, String> map) {

        // 拿到电话号码并判断电话号码位数
        String phoneNumber = map.get("phoneNumber");
        if (phoneNumber.length() != 11) throw new RuntimeException("电话号码格式错误");

        // 拿到redis对象
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        // 生成验证码
        String checkCode = RegisterUtils.getCheckCode();

        // 调用短信接口

        // 将验证码放入redis中，并设置过期时间为三分钟
        ops.set(phoneNumber + LoginConstant.VALUE_CREATE_CHECK_CODE, checkCode, Duration.ofMinutes(3));
    }

    /**
     * 用户注册
     * @param userRegisterVO 用户注册信息封装类
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void userRegister(UserRegisterVO userRegisterVO) {

        // 判断用户是否为空
        if (userRegisterVO == null) throw new RuntimeException("用户信息不能为空");

        // 判断验证码是否为空
        if (userRegisterVO.getCheckCode() == null) throw new RuntimeException("验证码不能为空");

        // 从redis中拿到验证码
        String redisCheckCode = (String) redisTemplate.opsForValue()
                .get(userRegisterVO.getPhoneNumber() + LoginConstant.VALUE_CREATE_CHECK_CODE);

        // 对比两个验证码
        if (!userRegisterVO.getCheckCode().equals(redisCheckCode)) throw new RuntimeException("验证码错误");

        // 验证码通过后，复制属性
        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(userRegisterVO, userPO);

        // 给用户 默认角色 8-- 只拥有查看酒店信息权限
        Integer defaultRoleId = 8;
        userPO.setRoleId(defaultRoleId);

        // 拿出密码，对密码进行加密,将加密后的密码重新放入
        String encodePassword = passwordEncoder.encode(userPO.getPassword());
        userPO.setPassword(encodePassword);

        // 消费总金额设为0
        userPO.setTotalConsumption(0);

        // 获取当前时间
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        Date date = calendar.getTime();

        // 给创建时间赋值
        userPO.setCreateDate(date);

        // 存入数据库
        userPOMapper.insert(userPO);
    }

    /**
     * 图片上传
     * @param request 请求
     * @param returnPicture 图片信息
     */
    @Override
    public void uploadReturnPicture(HttpServletRequest request, MultipartFile returnPicture)  {

        // 拿到token
        String token = request.getHeader("token");

        // 拿到用户id
        DecodedJWT verify = JWTUtils.verify(token);
        String userId = verify.getClaim("userId").asString();

        // 执行文件上传
        String headerPicture = null;
        try {
            headerPicture = OSSUtils.uploadFileToOss(ossProperties.getEndPoint(),
                    ossProperties.getAccessKeyId(),
                    ossProperties.getAccessKeySecret(),
                    returnPicture.getInputStream(),
                    ossProperties.getBucketName(),
                    ossProperties.getBucketDomain(),
                    returnPicture.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("上传失败");
        }

        // 判断
        if (headerPicture == null)
            throw new RuntimeException("上传失败");

        // 存入数据库
        userPOMapper.updateHeaderPictureById(Long.valueOf(userId), headerPicture);
    }
}

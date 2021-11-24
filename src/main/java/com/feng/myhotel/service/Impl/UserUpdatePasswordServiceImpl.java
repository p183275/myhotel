package com.feng.myhotel.service.Impl;

import com.feng.myhotel.entities.constant.ExceptionConstant;
import com.feng.myhotel.entities.constant.LoginConstant;
import com.feng.myhotel.entities.po.UserPO;
import com.feng.myhotel.entities.vo.UpdatePasswordVO;
import com.feng.myhotel.mapper.UserPOMapper;
import com.feng.myhotel.service.UserUpdatePasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : pcf
 * @date : 2021/11/12 20:55
 */
@Slf4j
@Service
public class UserUpdatePasswordServiceImpl implements UserUpdatePasswordService {

    @Autowired
    private UserPOMapper userPOMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 通过旧密码修改密码
     * @param updatePasswordVO 修改密码封装类
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void updatePasswordByOldPassword(UpdatePasswordVO updatePasswordVO) {

        // 通过账号查询数据库拿到信息对象
        UserPO userPO = userPOMapper.selectByLoginAccount(updatePasswordVO.getLoginAccount());

        // 判断拿到的对象是否为空，抛出无此账号
        if (userPO == null)
            throw new RuntimeException(ExceptionConstant.LOGIN_ACCOUNT_HAVE_NONE_MESSAGE);

        // 旧密码与原密码作比较
        boolean matches = passwordEncoder.matches(updatePasswordVO.getPassword(), userPO.getPassword());

        // 密码对比失败
        if (!matches)
            throw new RuntimeException(ExceptionConstant.PASSWORD_INPUT_ERROR_EXCEPTION);

        // 对比成功后将新密码加密
        String encodeNewPassword = passwordEncoder.encode(updatePasswordVO.getNewPassword());

        // 将新密码放入对象中
        userPO.setPassword(encodeNewPassword);

        // 修改数据库
        userPOMapper.updateByPrimaryKeySelective(userPO);

    }

    /**
     * 通过电话号码修改密码
     * @param updatePasswordVO 修改密码封装类
     */
    @Override
    public void updatePasswordByPhoneNumber(UpdatePasswordVO updatePasswordVO) {

        // 从参数中拿到手机号码和验证码
        String phoneNumber = updatePasswordVO.getPhoneNumber();
        String checkCode = updatePasswordVO.getCheckCode();

        // 根据电话号码查询数据库拿到用户对象
        UserPO userPO = userPOMapper.selectByPhoneNumber(phoneNumber);

        // 判断userPO是否为空
        if (userPO == null)
            throw new RuntimeException(ExceptionConstant.PHONE_INPUT_ERROR_EXCEPTION);

        // 从redis中拿到验证码
        String checkCodeFromRedis = (String) redisTemplate.opsForValue()
                .get(updatePasswordVO.getPhoneNumber() + LoginConstant.VALUE_CREATE_CHECK_CODE);

        // 如果查询不到验证码，则代表验证码到期
        if (checkCodeFromRedis == null)
            throw new RuntimeException(ExceptionConstant.CHECK_CODE_EXPIRE_EXCEPTION);

        // 对比两个验证码，是否错误
        if (!checkCodeFromRedis.equals(checkCode))
            throw new RuntimeException(ExceptionConstant.CHECK_CODE_INPUT_ERROR_EXCEPTION);

        // 正确之后对新密码加密后放入对象信息中
        String encodeNewPassword = passwordEncoder.encode(updatePasswordVO.getNewPassword());
        userPO.setPassword(encodeNewPassword);

        // 修改数据库
        userPOMapper.updateByPrimaryKeySelective(userPO);
    }
}

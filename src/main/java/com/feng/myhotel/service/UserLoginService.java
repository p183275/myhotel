package com.feng.myhotel.service;


import com.feng.myhotel.entities.vo.UserRegisterVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author : pcf
 * @date : 2021/11/11 9:19
 */
public interface UserLoginService {

    /**
     * 通过手机号码登录
     * @param map 封装电话号码的信息
     * @return 返回值（token）
     */
    Map<String, String> userLoginByPhone(Map<String, String> map);

    /**
     * 拿到验证码
     * @param map 电话号码键值对
     */
    void userGetCheckCode(Map<String, String> map);

    /**
     * 用户注册
     * @param userRegisterVO 用户注册信息封装类
     */
    void userRegister(UserRegisterVO userRegisterVO);

    /**
     * 执行图片上传请求
     * @param multipartFile 图片上传
     */
    void uploadReturnPicture(HttpServletRequest request, MultipartFile multipartFile);
}

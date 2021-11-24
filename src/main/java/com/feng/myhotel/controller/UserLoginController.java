package com.feng.myhotel.controller;

import com.feng.myhotel.config.OSSProperties;
import com.feng.myhotel.entities.result.CommonResult;
import com.feng.myhotel.entities.vo.UserRegisterVO;
import com.feng.myhotel.entities.vo.UserVO;
import com.feng.myhotel.service.UserLoginService;
import com.feng.myhotel.utils.OSSUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author : pcf
 * @date : 2021/11/11 9:17
 */
@RestController
@Api(tags = "用户登录方面控制器")
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @PostMapping(value = "/user/upload/header/picture")
    @ApiOperation(value = "上传用户图片：axios形式上传图片信息", httpMethod = "POST")
    public CommonResult<String> uploadReturnPicture(
            @ApiParam(value = "表单类型的图片信息", name = "returnPicture") @RequestParam("returnPicture") MultipartFile returnPicture,
                                                    HttpServletRequest request) {

        try {
            userLoginService.uploadReturnPicture(request , returnPicture);
            return new CommonResult<>(200, "上传成功");

        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "上传失败");
        }
    }


    @PostMapping(value = "/user/login/by/phone")
    @ApiOperation(value = "用户通过电话号码登录", httpMethod = "POST")
    public CommonResult<Map<String, String>> userLoginByPhone(
            @ApiParam(value = "电话号码和验证码键值对", name = "map", example = "phoneNumber:123") @RequestBody Map<String, String> map){

        try {
            // 如果成功
            Map<String, String> resultMap = userLoginService.userLoginByPhone(map);
            return new CommonResult<>(200, "登录成功", resultMap);

        } catch (Exception exception) {
            // 如果失败
            exception.printStackTrace();
            return new CommonResult<>(400, "登录失败," + exception.getMessage());
        }
    }

    @PostMapping(value = "/user/register")
    @ApiOperation(value = "用户注册", httpMethod = "POST")
    public CommonResult<String> userRegister(
            @ApiParam(value = "注册信息封装", name = "userRegisterVO") @RequestBody UserRegisterVO userRegisterVO){

        try {
            // 如果成功
            userLoginService.userRegister(userRegisterVO);
            return new CommonResult<>(200, "注册成功");

        } catch (Exception exception) {
            // 如果发生异常
            exception.printStackTrace();
            return new CommonResult<>(400, "注册失败" + exception.getMessage());
        }

    }

    /**
     * 获取验证码
     * @param map 电话号码
     * @return json消息
     */
    @PostMapping(value = "/user/get/check/code")
    @ApiOperation(value = "发送信息获取验证码", httpMethod = "POST")
    public CommonResult<String> userGetCheckCode(
            @ApiParam(value = "电话号码", name = "map", example = "phoneNumber:123") @RequestBody Map<String, String> map){

        try {
            // 如果成功
            userLoginService.userGetCheckCode(map);
            return new CommonResult<>(200, "获取验证码成功");

        } catch (Exception exception) {
            // 如果发生异常
            exception.printStackTrace();
            return new CommonResult<>(400, "获取验证码失败" + exception.getMessage());
        }

    }

}

package com.feng.myhotel.controller;

import com.feng.myhotel.entities.result.CommonResult;
import com.feng.myhotel.entities.vo.UpdatePasswordVO;
import com.feng.myhotel.service.UserUpdatePasswordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : pcf
 * @date : 2021/11/12 20:54
 */
@RestController
@Api(tags = "用户更改密码控制器")
public class UserUpdatePasswordController {

    @Autowired
    private UserUpdatePasswordService userUpdatePasswordService;

    /**
     * 通过旧密码修改密码
     * @param updatePasswordVO 封装修改信息的对象
     * @return 成功或失败信息
     */
    @PostMapping(value = "/user/update/by/password")
    @ApiOperation(value = "通过旧密码修改密码", httpMethod = "POST")
    public CommonResult<String> updatePasswordByOldPassword(
           @ApiParam(value = "封装修改信息的对象", name = "updatePasswordVO") @RequestBody UpdatePasswordVO updatePasswordVO){

        try {
            // 如果成功
            userUpdatePasswordService.updatePasswordByOldPassword(updatePasswordVO);
            return new CommonResult<>(200, "修改密码成功");

        } catch (Exception exception) {
            // 如果失败
            exception.printStackTrace();
            return new CommonResult<>(400, "修改密码失败 " + exception.getMessage());
        }

    }

    @PostMapping(value = "/user/update/by/phone")
    @ApiOperation(value = "通过电话号码修改密码", httpMethod = "POST")
    public CommonResult<String> updatePasswordByPhoneNumber(
            @ApiParam(value = "封装修改信息的对象", name = "updatePasswordVO") @RequestBody UpdatePasswordVO updatePasswordVO){

        try {
            // 如果成功
            userUpdatePasswordService.updatePasswordByPhoneNumber(updatePasswordVO);
            return new CommonResult<>(200, "修改密码成功");

        } catch (Exception exception) {
            // 如果失败
            exception.printStackTrace();
            return new CommonResult<>(400, "修改密码失败 " + exception.getMessage());
        }
    }

}

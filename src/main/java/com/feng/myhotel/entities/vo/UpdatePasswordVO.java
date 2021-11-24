package com.feng.myhotel.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author : pcf
 * @date : 2021/11/12 21:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "修改密码封装类")
public class UpdatePasswordVO {

    @ApiModelProperty(value = "账号")
    private String loginAccount;

    @ApiModelProperty(value = "旧密码")
    private String password;

    @ApiModelProperty(value = "新密码")
    private String newPassword;

    @ApiModelProperty(value = "电话号码")
    private String phoneNumber;

    @ApiModelProperty(value = "验证码")
    private String checkCode;

}

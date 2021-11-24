package com.feng.myhotel.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author : pcf
 * @date : 2021/11/11 21:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "用户注册的封装类")
public class UserRegisterVO {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别", example = "0", notes = "0--女，1--男")
    private Integer gender;

    @ApiModelProperty(value = "账号")
    private String loginAccount;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "电话号码")
    private String phoneNumber;

    @ApiModelProperty(value = "验证码")
    private String checkCode;

    @ApiModelProperty(value = "角色id", example = "1")
    private Integer roleId;

}

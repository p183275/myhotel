package com.feng.myhotel.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : pcf
 * @date : 2021/11/11 18:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "用户封装类")
public class UserVO implements Serializable {

    @ApiModelProperty(value = "用户id", example = "1")
    private Long id;

    @ApiModelProperty(value = "性别", example = "0", notes = "0--女，1--男")
    private Integer gender;

    @ApiModelProperty(value = "姓名")
    private String name;

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

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "总消费金额", example = "10000")
    private Integer totalConsumption;

    @ApiModelProperty(value = "图片路径")
    private String headerPicture;

    @ApiModelProperty(value = "创建日期")
    private Date createDate;
}

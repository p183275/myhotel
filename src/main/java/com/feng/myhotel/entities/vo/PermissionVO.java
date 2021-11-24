package com.feng.myhotel.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * @author : pcf
 * @date : 2021/11/11 19:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "权限封装类欸")
public class PermissionVO {

    @ApiModelProperty(value = "权限id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "父菜单id", example = "1")
    private Integer pid;

    @ApiModelProperty(value = "访问路径")
    private String path;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "权限名称")
    private String permissionName;

    @ApiModelProperty(value = "权限值")
    private String permissionValue;

}

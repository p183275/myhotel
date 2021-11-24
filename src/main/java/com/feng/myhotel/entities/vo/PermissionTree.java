package com.feng.myhotel.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : pcf
 * @date : 2021/11/20 19:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "权限菜单属性结构生成")
public class PermissionTree {

    @ApiModelProperty(value = "权限id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "父菜单id", example = "1")
    private Integer pid;

    @ApiModelProperty(value = "权限名称")
    private String permissionName;

    @ApiModelProperty(value = "子菜单")
    private List<PermissionTree> child = new ArrayList<>();
}

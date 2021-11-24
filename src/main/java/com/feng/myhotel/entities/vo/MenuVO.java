package com.feng.myhotel.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author : pcf
 * @date : 2021/11/14 15:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "菜单封装类")
public class MenuVO {

    @ApiModelProperty(value = "菜单id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "父菜单id", example = "1")
    private Integer pid;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "菜单链接")
    private String path;

    @ApiModelProperty(value = "图标样式")
    private String icon;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

}

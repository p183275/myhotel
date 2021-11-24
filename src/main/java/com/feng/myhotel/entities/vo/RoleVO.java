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
 * @date : 2021/11/11 19:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "角色封装类欸")
public class RoleVO {

    @ApiModelProperty(value = "角色id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色信息")
    private String remark;

    @ApiModelProperty(value = "创建日期")
    private Date createDate;

    @ApiModelProperty(value = "权限id回显")
    private List<Integer> ownAuthIdList;

}

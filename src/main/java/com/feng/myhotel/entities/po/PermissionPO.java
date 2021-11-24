package com.feng.myhotel.entities.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author : pcf
 * @date : 2021/11/11 19:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PermissionPO {

    private Integer id;

    private Integer pid;

    private String permissionName;

    private Integer type;

    private String permissionValue; // 权限值

    private String path; // 访问路径

    private String icon; // 图标

    private Date createDate; // 创建日期

}

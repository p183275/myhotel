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
public class RolePO {

    private Integer id;

    private String roleName;

    private String remark; // 注释

    private Date createDate; // 创建日期
}

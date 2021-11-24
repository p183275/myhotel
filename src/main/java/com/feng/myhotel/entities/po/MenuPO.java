package com.feng.myhotel.entities.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author : pcf
 * @date : 2021/11/14 15:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MenuPO {

    private Integer id;

    private Integer pid;

    private String menuName;

    private Integer type;

    private String path;

    private String icon;

    private Date createDate;

}

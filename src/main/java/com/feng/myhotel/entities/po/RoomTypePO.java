package com.feng.myhotel.entities.po;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author : pcf
 * @date : 2021/11/14 16:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "房间类型封装类")
public class RoomTypePO {

    @ApiModelProperty(value = "房间类型id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "房间类型名")
    private String name;

    @ApiModelProperty(value = "房间类型备注")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

}

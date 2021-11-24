package com.feng.myhotel.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : pcf
 * @date : 2021/11/14 22:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "房间封装类")
public class RoomVO {

    @ApiModelProperty(value = "房间id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "楼层")
    private String storey; // 楼层

    @ApiModelProperty(value = "房间编号", example = "F1-101")
    private String number; // 房间编号

    @ApiModelProperty(value = "房间类型id", example = "1")
    private Integer typeId;

    @ApiModelProperty(value = "房间类型", example = "单人房")
    private String type; // 房间类型

    @ApiModelProperty(value = "房间价格", example = "101.11")
    private BigDecimal price; // 房间价格

    @ApiModelProperty(value = "房间备注信息")
    private String remark; // 房间备注信息

    @ApiModelProperty(value = "房间状态", example = "0")
    private Integer status; // 房间状态

//    @ApiModelProperty(value = "更新时间", notes = "前端不需要传此参数")
//    private Date updateTime; // 更新时间
//
//    @ApiModelProperty(value = "更新时间", notes = "前端不需要传此参数")
//    private Date createTime; // 创建时间

}

package com.feng.myhotel.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author : pcf
 * @date : 2021/11/21 16:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "订单数量统计类")
public class OrderNumberVO {

    @ApiModelProperty(value = "主键", example = "1")
    private Integer id;

    @ApiModelProperty(value = "房间订单数量", example = "1")
    private Long roomOrderNumber;

    @ApiModelProperty(value = "餐饮订单数量", example = "1")
    private Long foodOrderNumber;

    @ApiModelProperty(value = "创建日期")
    private Date createDate;

}

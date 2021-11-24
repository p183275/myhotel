package com.feng.myhotel.entities.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author : pcf
 * @date : 2021/11/18 17:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DepositAndOrderVO {

    @ApiModelProperty(value = "押金id")
    private Long depositId;

    @ApiModelProperty(value = "押金金额")
    private BigDecimal deposit;

    @ApiModelProperty(value = "订单封装类")
    private RoomOrderVO roomOrderVO;

}

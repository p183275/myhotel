package com.feng.myhotel.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author : pcf
 * @date : 2021/11/18 15:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "收银管理之客房付款接收类")
public class CashierPayVO {

    @ApiModelProperty(value = "订单的主键", example = "1")
    private Long roomOrderId;

    @ApiModelProperty(value = "押金金额", example = "100.00")
    private BigDecimal deposit;

}

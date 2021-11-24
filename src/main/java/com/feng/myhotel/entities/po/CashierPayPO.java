package com.feng.myhotel.entities.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author : pcf
 * @date : 2021/11/18 15:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CashierPayPO {

    private Long id;

    private BigDecimal deposit;

    private Long roomOrderId; // 订单主键

    private String status;

}

package com.feng.myhotel.entities.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomOrderPO {
    private Long id;

    private String orderId;

    private Integer roomId;

    private Long userId;

    private String userName;

    private String phoneNumber;

    private Integer gender;

    private String roomType;

    private BigDecimal price;

    private Integer credentials;

    private String credentialsCode;

    private String address;

    private String content;

    private String status;

    private String payStatus;

    private Date beginDate;

    private Date endDate;

    private Date createDate;

    private Date orderEndDate;

}
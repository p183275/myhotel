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
 * @date : 2021/11/16 8:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "房间订单信息封装类")
public class RoomOrderVO {

    @ApiModelProperty(value = "主键", example = "1")
    private Long id;

    @ApiModelProperty(value = "订单号", example = "ds13f3545213143")
    private String orderId;

    @ApiModelProperty(value = "房间id", example = "1")
    private Integer roomId;

    @ApiModelProperty(value = "用户id", example = "1")
    private Long userId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户电话")
    private String phoneNumber;

    @ApiModelProperty(value = "用户性别 0--女 1--男", example = "1")
    private Integer gender;

    @ApiModelProperty(value = "房间类型")
    private String roomType;

    @ApiModelProperty(value = "订单金额", example = "100.00")
    private BigDecimal price;

    @ApiModelProperty(value = "证件类型", example = "1", notes = "0--身份证 1--驾驶证……")
    private Integer credentials;

    @ApiModelProperty(value = "证件号码")
    private String credentialsCode;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "备注")
    private String content;

    @ApiModelProperty(value = "订单状态", notes = "0--预定中 1--生效中 2--已结束")
    private String status;

    @ApiModelProperty(value = "预定开始时间")
    private Date beginDate;

    @ApiModelProperty(value = "预定结束时间")
    private Date endDate;

    @ApiModelProperty(value = "订单创建时间")
    private Date createDate;

    @ApiModelProperty(value = "订单结束时间")
    private Date orderEndDate;

}

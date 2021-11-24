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
 * @date : 2021/11/16 22:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "封装生成订单的类")
public class RoomReceiveVO {

    @ApiModelProperty(value = "房间id", example = "1")
    private Integer roomId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户电话")
    private String phoneNumber;

    @ApiModelProperty(value = "用户性别 0--女 1--男", example = "1")
    private Integer gender;

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

    @ApiModelProperty(value = "预定开始时间")
    private Date beginDate;

    @ApiModelProperty(value = "预定结束时间")
    private Date endDate;

}

package com.feng.myhotel.entities.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : pcf
 * @date : 2021/11/14 16:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomPO {

    private Integer id;

    private String storey; // 楼层

    private String number; // 房间编号

    private Integer typeId; // 房间类型编号

    private String type; // 房间类型

    private BigDecimal price; // 房间价格

    private String remark; // 房间备注信息

    private Integer status; // 房间状态

    private Date updateTime; // 更新时间

    private Date createTime; // 创建时间

}

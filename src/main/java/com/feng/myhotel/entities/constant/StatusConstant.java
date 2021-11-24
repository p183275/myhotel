package com.feng.myhotel.entities.constant;

/**
 * 状态常用类
 * @author : pcf
 * @date : 2021/11/22 17:13
 */
public class StatusConstant {

    // 房间
    public static final Integer ROOM_STATUS_FREE = 0;
    public static final Integer ROOM_STATUS_USING = 1;
    public static final Integer ROOM_STATUS_DIRTY = 2;

    // 订单付款状态
    public static final String ORDER_PAY_STATUS_NO_PAY = "0";
    public static final String ORDER_PAY_STATUS_PAY = "1";

    // 订单状态
    public static final String ORDER_STATUS_NO_EFFECT = "0";
    public static final String ORDER_STATUS_EFFECT = "1";
    public static final String ORDER_STATUS_ENDING = "2";

    // 押金状态
    public static final String DEPOSIT_STATUS_NOT_FINISH = "0";
    public static final String DEPOSIT_STATUS_FINISH = "1";
}

package com.feng.myhotel.service;

import com.feng.myhotel.entities.vo.RoomReceiveVO;

import java.util.Map;

/**
 * @author : pcf
 * @date : 2021/11/17 23:19
 */
public interface RoomReceiveService {

    /**
     * 接待管理之客户住房
     * @param roomReceiveVO 信息封装类
     */
    void receiveRoom(RoomReceiveVO roomReceiveVO);

    /**
     * 接待管理之客户退房
     * @param id 订单主键
     */
    void checkOutRoom(Map<String, Integer> id);

}

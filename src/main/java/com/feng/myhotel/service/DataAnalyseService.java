package com.feng.myhotel.service;

import com.feng.myhotel.entities.vo.OrderNumberVO;

import java.util.List;
import java.util.Map;

/**
 * @author : pcf
 * @date : 2021/11/21 16:27
 */
public interface DataAnalyseService {

    void insertOrderNumber(OrderNumberVO orderNumberVO);

    List<OrderNumberVO> getAllOrderNumber();

    /**
     * 从redis中获取性别数量
     * @return map
     */
    Map<Integer, Integer> getGenderNumberFromRedis();

}

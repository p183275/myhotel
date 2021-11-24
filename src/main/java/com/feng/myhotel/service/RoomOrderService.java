package com.feng.myhotel.service;

import com.feng.myhotel.entities.vo.RoomOrderVO;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author : pcf
 * @date : 2021/11/15 23:22
 */
public interface RoomOrderService {

    /**
     * 拿到各种状态的订单信息
     * @param pageSize 当前页面的最大显示量
     * @param pageIndex 当前页数
     * @param status 订单状态
     * @return 封装类
     */
    PageInfo<RoomOrderVO> getAllRoomOrder(Integer pageSize, Integer pageIndex, String status);

    /**
     * 分页拿到所有订单信息
     * @param pageSize 当前页面现实的数据量
     * @param pageIndex 当前页数
     * @return 封装类
     */
    PageInfo<RoomOrderVO> getAllRoomOrderByPage(Integer pageSize, Integer pageIndex);

    /**
     * 分配房间
     * @param map 分配房间
     */
    void distributeRoom(Map<String, String> map);

    /**
     * 删除订单
     * @param idMap 封装id的map
     */
    void deleteRoomOrders(Map<String, List<Long>> idMap);

}

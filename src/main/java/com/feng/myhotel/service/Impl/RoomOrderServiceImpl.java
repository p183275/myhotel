package com.feng.myhotel.service.Impl;

import com.feng.myhotel.entities.constant.ExceptionConstant;
import com.feng.myhotel.entities.constant.StatusConstant;
import com.feng.myhotel.entities.vo.RoomOrderVO;
import com.feng.myhotel.mapper.DepositMapper;
import com.feng.myhotel.mapper.RoomMapper;
import com.feng.myhotel.mapper.RoomOrderPOMapper;
import com.feng.myhotel.service.RoomOrderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author : pcf
 * @date : 2021/11/15 23:22
 */
@Service
public class RoomOrderServiceImpl implements RoomOrderService {

    @Autowired
    private RoomOrderPOMapper roomOrderPOMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private DepositMapper depositMapper;

    /**
     * 拿到指定状态的订单
     * @param pageSize 当前页面的最大显示量
     * @param pageIndex 当前页数
     * @param status 订单状态
     * @return 订单列表
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<RoomOrderVO> getAllRoomOrder(Integer pageSize, Integer pageIndex, String status) {

        // 调用分页插件
        PageHelper.startPage(pageIndex, pageSize);

        // 拿到正在预定，且已经付款的
        List<RoomOrderVO> orderVOList = roomOrderPOMapper.selectAllReserveRoomOrder(status);

        // 封装信息
        PageInfo<RoomOrderVO> pageInfo = new PageInfo<>(orderVOList);

        return pageInfo;
    }

    /**
     * 分页查询所有订单
     * @param pageSize 当前页面现实的数据量
     * @param pageIndex 当前页数
     * @return 分页信息列表
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<RoomOrderVO> getAllRoomOrderByPage(Integer pageSize, Integer pageIndex) {

        // 调用分页插件
        PageHelper.startPage(pageIndex, pageSize);

        // 查询
        List<RoomOrderVO> roomOrderVOList = roomOrderPOMapper.selectAllRoomOrder();

        // 封装信息
        PageInfo<RoomOrderVO> pageInfo = new PageInfo<>(roomOrderVOList);

        return pageInfo;
    }

    /**
     * 分配房间
     * @param map orderId、roomId
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void distributeRoom(Map<String, String> map) {

        // 从map中拿到订单id和房间id
        String stringId = map.get("id");
        String stringRoomId = map.get("roomId");

        // 判断
        if (stringId == null || stringRoomId == null)
            throw new RuntimeException(ExceptionConstant.DATA_IS_NOT_NULL_EXCEPTION);

        // 将id转化为需要的类型
        Long orderId = Long.valueOf(stringId);
        Integer roomId = Integer.parseInt(stringRoomId);

        // 修改房间status为1--正在使用
        Integer roomStatus = StatusConstant.ROOM_STATUS_USING;
        roomMapper.updateRoomStatusById(roomId, roomStatus);

        // 修改数据库 设置订单状态为1--生效中
        String status = "1";
        roomOrderPOMapper.updateRoomIdByOrderId(orderId, roomId, status);
    }

    /**
     * 删除订单
     * @param idMap 封装id的map
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteRoomOrders(Map<String, List<Long>> idMap) {

        // 从map中拿到需要删除的id
        List<Long> idList = idMap.get("idList");

        // 判断
        if (idList == null || idList.size() == 0)
            throw new RuntimeException(ExceptionConstant.DATA_IS_NOT_NULL_EXCEPTION);

        // 删除押金表
        depositMapper.deleteDepositByOrderIdList(idList);

        // 从数据库中删除订单
        roomOrderPOMapper.deleteRoomOrderById(idList);
    }
}

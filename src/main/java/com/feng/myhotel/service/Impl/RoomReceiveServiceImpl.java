package com.feng.myhotel.service.Impl;

import cn.hutool.core.util.IdUtil;
import com.feng.myhotel.entities.constant.ExceptionConstant;
import com.feng.myhotel.entities.constant.RedisConstant;
import com.feng.myhotel.entities.constant.StatusConstant;
import com.feng.myhotel.entities.po.RoomOrderPO;
import com.feng.myhotel.entities.vo.RoomOrderVO;
import com.feng.myhotel.entities.vo.RoomReceiveVO;
import com.feng.myhotel.mapper.RoomMapper;
import com.feng.myhotel.mapper.RoomOrderPOMapper;
import com.feng.myhotel.mapper.RoomTypeMapper;
import com.feng.myhotel.service.RedisService;
import com.feng.myhotel.service.RoomReceiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * @author : pcf
 * @date : 2021/11/17 23:19
 */
@Service
@Slf4j
public class RoomReceiveServiceImpl implements RoomReceiveService {

    @Autowired
    private RoomOrderPOMapper roomOrderPOMapper;
    @Autowired
    private RoomTypeMapper roomTypeMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private RedisService redisService;

    /**
     * 客户住房
     * @param roomReceiveVO 订单信息封装类
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void receiveRoom(RoomReceiveVO roomReceiveVO) {

        // 判断信息是否为空
        if (roomReceiveVO == null)
            throw new RuntimeException(ExceptionConstant.DATA_IS_NOT_NULL_EXCEPTION);

        // 创建po对象
        RoomOrderPO roomOrderPO = new RoomOrderPO();

        // 复制属性
        BeanUtils.copyProperties(roomReceiveVO, roomOrderPO);

        // 生成订单id，并放入po对象
        String orderId = IdUtil.simpleUUID();
        roomOrderPO.setOrderId(orderId);

        // 通过roomId，查询roomType
        String roomType = roomTypeMapper.selectTypeNameByRoomId(roomOrderPO.getRoomId());
        roomOrderPO.setRoomType(roomType);

        // 判断是否存在roomType
        if (roomType == null)
            throw new RuntimeException(ExceptionConstant.ROOM_HAVE_NONE_MESSAGE);

        // 修改当前订单的status为0预定中 0--预定中 1--生效中 2--已结束
        String status = StatusConstant.ORDER_STATUS_NO_EFFECT;
        roomOrderPO.setStatus(status);

        // 修改订单的付款状态为0-未付款 0--未付款 1--已付款
        String payStatus = StatusConstant.ORDER_PAY_STATUS_NO_PAY;
        roomOrderPO.setPayStatus(payStatus);

        // user_id设为null
        roomOrderPO.setUserId(null);

        // 获取当前时间
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Date time = calendar.getTime();

        // 更改po对象的创建时间
        roomOrderPO.setCreateDate(time);

        // 修改房间状态为未打扫卫生  0--未使用 1--正在使用 2--未打扫卫生
        Integer roomStatus = StatusConstant.ROOM_STATUS_DIRTY;
        roomMapper.updateRoomStatusById(roomOrderPO.getRoomId(), roomStatus);

        // 存入数据库
        roomOrderPOMapper.insert(roomOrderPO);

        // 使redis中的订单数量统计自增
        String key = RedisConstant.ROOM_ORDER_NUMBER_KEY;
        redisService.orderNumIncr(key);

        // 使得redis中对应性别数量自增
        Integer gender = roomOrderPO.getGender();
        redisService.genderIncr(gender);
    }

    /**
     * 客户退房
     * @param idMap 封装id的map
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void checkOutRoom(Map<String, Integer> idMap) {

        // 判断idMap是否为空
        if (idMap == null)
            throw new RuntimeException(ExceptionConstant.DATA_IS_NOT_NULL_EXCEPTION);

        // 从idMap中获取id
        Integer id = idMap.get("id");

        log.info("********房间id" + id);

        // 判断id是否为空
        if (id == null)
            throw new RuntimeException("数据不能为空");

        // 通过房间id拿到订单
        RoomOrderVO roomOrderVO = roomOrderPOMapper.selectRoomOrderVOById(id);

        // 设置房间status为2--未打扫状态
        Integer roomStatus = StatusConstant.ROOM_STATUS_DIRTY;
        roomMapper.updateRoomStatusById(roomOrderVO.getRoomId(), roomStatus);

        // 拿到当前时间
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Date time = calendar.getTime();

        // 不为空则执行更新订单操作
        // 2--订单已结束
        String orderStatus = StatusConstant.ORDER_STATUS_ENDING;
        roomOrderPOMapper.updateOrderStatusAndDateById(roomOrderVO.getId(), orderStatus, time);
    }
}

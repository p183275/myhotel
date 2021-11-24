package com.feng.myhotel.service.Impl;

import com.feng.myhotel.entities.constant.StatusConstant;
import com.feng.myhotel.entities.po.RoomPO;
import com.feng.myhotel.entities.po.RoomTypePO;
import com.feng.myhotel.entities.vo.RoomVO;
import com.feng.myhotel.mapper.RoomMapper;
import com.feng.myhotel.mapper.RoomTypeMapper;
import com.feng.myhotel.service.RoomService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author : pcf
 * @date : 2021/11/14 23:35
 */
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private RoomTypeMapper roomTypeMapper;

    /**
     * 拿到所有房间类型的信息
     * @return 房间列表
     */
    @Override
    public List<RoomTypePO> getAllRoomType() {

        return roomTypeMapper.selectAllType();
    }

    /**
     * 拿到所有房间信息
     * @return 房间信息列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<RoomVO> getAllRoom() {
        return roomMapper.selectAllRoom();
    }

    /**
     * 修改房间信息
     * @param roomVO 房间信息封装类
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateRoom(RoomVO roomVO) {

        // 拿到房间信息，判断其是否为空
        if (roomVO == null)
            throw new RuntimeException("数据不能为空！");

        // 创建po对象，并复制属性
        RoomPO roomPO = new RoomPO();
        BeanUtils.copyProperties(roomVO, roomPO);

        // 拿到当前时间
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Date update_time = calendar.getTime();

        // 把更新时间放入po对象
        roomPO.setUpdateTime(update_time);

        // 更新数据库
        roomMapper.updateRoom(roomPO);
    }

    /**
     * 增加房间
     * @param roomVO 房间信息封装类
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertRoom(RoomVO roomVO) {

        // 拿到对象判断是否为空
        if (roomVO == null)
            throw new RuntimeException("数据不能为空！");

        // 创建po对象并复制属性
        RoomPO roomPO = new RoomPO();
        BeanUtils.copyProperties(roomVO, roomPO);

        // 通过typeId查询typePO对象，将type存入roomPO
        RoomTypePO roomTypePO = roomTypeMapper.selectRoomTypeById(roomPO.getTypeId());
        roomPO.setType(roomTypePO.getName());

        // 将po对象id置为空(数据库自动递增)
        roomPO.setId(null);

        // 将status设为0
        Integer roomStatus = StatusConstant.ROOM_STATUS_FREE;
        roomPO.setStatus(roomStatus);

        // 拿到当前时间
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Date time = calendar.getTime();

        // 修改更新时间和创建时间
        roomPO.setUpdateTime(time);
        roomPO.setCreateTime(time);

        // 存入数据库
        roomMapper.insertRoom(roomPO);
    }

    /**
     * 通过id删除房间信息
     * @param idListMap id列表
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteRoomById(Map<String, List<Integer>> idListMap) {

        // 拿到idList
        List<Integer> idList = idListMap.get("idList");

        // 判断id是否为空
        if (idList == null || idList.size() == 0)
            throw new RuntimeException("数据不能为空");

        // 从数据库中删除
        roomMapper.deleteRoomByIdList(idList);
    }

}

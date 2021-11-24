package com.feng.myhotel.mapper;

import com.feng.myhotel.entities.po.RoomPO;
import com.feng.myhotel.entities.vo.RoomVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : pcf
 * @date : 2021/11/14 16:24
 */
@Mapper
public interface RoomMapper {

    /**
     * 拿到所有房间信息
     * @return 房间信息的封装类
     */
    List<RoomVO> selectAllRoom();

    /**
     * 更新房间信息
     * @param roomPO 房间信息
     */
    void updateRoom(@Param("roomPO") RoomPO roomPO);

    /**
     * 增加房间
     * @param roomPO 房间信息
     */
    void insertRoom(@Param("roomPO") RoomPO roomPO);

    /**
     * 通过id删除房间信息
     * @param idList id
     */
    void deleteRoomByIdList(@Param("idList")List<Integer> idList);

    /**
     * 修改room的status
     * @param id roomId
     * @param status 房间状态
     */
    void updateRoomStatusById(@Param("id")Integer id, @Param("status")Integer status);

    /**
     * 通过订单id修改房间状态
     * @param orderId 订单id
     * @param status 房间状态
     */
    void updateRoomStatusByOrderId(@Param("orderId")Long orderId, @Param("status")Integer status);

}

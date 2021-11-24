package com.feng.myhotel.mapper;

import java.util.Date;
import java.util.List;

import com.feng.myhotel.entities.po.RoomOrderPO;
import com.feng.myhotel.entities.po.RoomOrderPOExample;
import com.feng.myhotel.entities.vo.RoomOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoomOrderPOMapper {
    int countByExample(RoomOrderPOExample example);

    int deleteByExample(RoomOrderPOExample example);

    int deleteByPrimaryKey(Long id);

    void deleteRoomOrderById(@Param("idList") List<Long> idList);

    int insert(RoomOrderPO record);

    int insertSelective(RoomOrderPO record);

    List<RoomOrderPO> selectByExample(RoomOrderPOExample example);

    RoomOrderPO selectByPrimaryKey(Long id);

    RoomOrderVO selectRoomOrderVOByRoomId(@Param("roomId") Integer roomId);

    RoomOrderVO selectRoomOrderVOById(@Param("id") Integer id);

    List<RoomOrderVO> selectAllRoomOrder();

    List<RoomOrderVO> selectAllReserveRoomOrder(@Param("status") String status);

    List<RoomOrderVO> selectUnPayReserveRoomOrder(@Param("payStatus") String payStatus);

    void updatePayStatusAndStatus(@Param("roomOrderId")Long roomOrderId , @Param("payStatus")String payStatus, @Param("status") String status);

    int updateByExampleSelective(@Param("record") RoomOrderPO record, @Param("example") RoomOrderPOExample example);

    int updateByExample(@Param("record") RoomOrderPO record, @Param("example") RoomOrderPOExample example);

    int updateByPrimaryKeySelective(RoomOrderPO record);

    void updateOrderStatusAndDateById(@Param("id") Long id, @Param("orderStatus") String orderStatus, @Param("date") Date date);

    void updateRoomIdByOrderId(@Param("orderId") Long orderId, @Param("roomId") Integer roomId,
                               @Param("status") String status);

    int updateByPrimaryKey(RoomOrderPO record);
}
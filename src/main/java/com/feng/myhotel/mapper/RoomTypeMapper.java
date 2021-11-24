package com.feng.myhotel.mapper;

import com.feng.myhotel.entities.po.RoomTypePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : pcf
 * @date : 2021/11/14 19:24
 */
@Mapper
public interface RoomTypeMapper {

    /**
     * 拿到所有房间类型
     * @return 房间类型列表
     */
    List<RoomTypePO> selectAllType();

    /**
     * 通过id拿到房间类型
     * @param id id
     * @return 房间封装类
     */
    RoomTypePO selectRoomTypeById(@Param("id") Integer id);

    /**
     * 通过id拿到房间类型名称
     * @param id id
     * @return 房间封装类
     */
    String selectTypeNameById(@Param("id") Integer id);

    /**
     * 通过房间id拿到room类型
     * @param roomId 房间id
     * @return room类型
     */
    String selectTypeNameByRoomId(@Param("roomId")Integer roomId);

}

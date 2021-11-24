package com.feng.myhotel.service;

import com.feng.myhotel.entities.po.RoomTypePO;
import com.feng.myhotel.entities.vo.RoomVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author : pcf
 * @date : 2021/11/14 23:35
 */
public interface RoomService {

    /**
     * 拿到所有房间类型信息
     * @return 房间类型列表
     */
    List<RoomTypePO> getAllRoomType();

    /**
     * 拿到所有房间信息
     * @return 房间类型列表
     */
    List<RoomVO> getAllRoom();

    /**
     * 修改房间信息
     * @param roomVO 房间信息封装类
     */
    void updateRoom(RoomVO roomVO);

    /**
     * 增加房间
     * @param roomVO 房间信息封装类
     */
    void insertRoom(RoomVO roomVO);


    /**
     * 通过id删除房间信息
     * @param idListMap id
     */
    void deleteRoomById(Map<String, List<Integer>> idListMap);
}
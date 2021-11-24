package com.feng.myhotel.mapper;

import com.feng.myhotel.entities.vo.OrderNumberVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : pcf
 * @date : 2021/11/21 16:11
 */
@Mapper
public interface OrderNumberMapper {

    List<OrderNumberVO> getAllOrderNumber();

    void insertOrderNumber(@Param("orderNumberVO") OrderNumberVO orderNumberVO);

}

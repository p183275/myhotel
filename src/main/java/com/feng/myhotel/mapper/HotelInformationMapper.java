package com.feng.myhotel.mapper;

import com.feng.myhotel.entities.po.HotelInformationPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author : pcf
 * @date : 2021/11/14 22:17
 */
@Mapper
public interface HotelInformationMapper {

    /**
     * 查询酒店信息
     * @return 酒店信息
     */
    HotelInformationPO selectHotelInformation();

    /**
     * 修改酒店信息
     * @param hotelInformationPO 酒店信息封装类
     */
    void updateHotelInformation(@Param("hotelInformationPO") HotelInformationPO hotelInformationPO);

    /**
     * 上传图片
     * @param headerPicture 图片
     */
    void updateHeaderPictureById(@Param("headerPicture") String headerPicture);
}

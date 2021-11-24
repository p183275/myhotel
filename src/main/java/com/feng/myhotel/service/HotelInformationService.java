package com.feng.myhotel.service;

import com.feng.myhotel.entities.vo.HotelInformationVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author : pcf
 * @date : 2021/11/14 22:25
 */
public interface HotelInformationService {

    /**
     * 获取酒店信息
     * @return 酒店信息
     */
    HotelInformationVO getHotelInformation(HttpServletRequest request, Map<String, String> map);

    /**
     * 更新酒店信息
     * @param hotelInformationVO 酒店信息封装类
     */
    void updateHotelInformation(HotelInformationVO hotelInformationVO);

    /**
     * 执行图片上传请求
     * @param multipartFile 图片上传
     */
    void uploadReturnPicture(MultipartFile multipartFile);

}

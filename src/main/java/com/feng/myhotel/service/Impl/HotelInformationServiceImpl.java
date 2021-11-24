package com.feng.myhotel.service.Impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.feng.myhotel.config.OSSProperties;
import com.feng.myhotel.entities.constant.ExceptionConstant;
import com.feng.myhotel.entities.po.HotelInformationPO;
import com.feng.myhotel.entities.vo.HotelInformationVO;
import com.feng.myhotel.mapper.HotelInformationMapper;
import com.feng.myhotel.mapper.PermissionPOMapper;
import com.feng.myhotel.service.HotelInformationService;
import com.feng.myhotel.utils.JWTUtils;
import com.feng.myhotel.utils.OSSUtils;
import com.sun.corba.se.impl.oa.toa.TOA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @author : pcf
 * @date : 2021/11/14 22:25
 */
@Slf4j
@Service
public class HotelInformationServiceImpl implements HotelInformationService {

    @Autowired
    private HotelInformationMapper hotelInformationMapper;
    @Autowired
    private OSSProperties ossProperties;
    @Autowired
    private PermissionPOMapper permissionPOMapper;

    /**
     * 获取酒店信息
     * @return 酒店信息封装类
     */
    @Override
    public HotelInformationVO getHotelInformation(HttpServletRequest request, Map<String, String> map) {

        // 拿到token
        String token = request.getHeader("token");

        // 拿到当前角色的id
        DecodedJWT verify = JWTUtils.verify(token);
        String roleId = verify.getClaim("roleId").asString();

        // 从map中拿到permissionId
        String permissionId = map.get("id");

        // 取出按钮信息
        List<String> buttonList = permissionPOMapper.selectButtonList(Integer.parseInt(permissionId),
                Integer.parseInt(roleId));

        // 获取酒店信息
        HotelInformationPO hotelInformationPO = hotelInformationMapper.selectHotelInformation();

        // 创建vo对象
        HotelInformationVO hotelInformationVO = new HotelInformationVO();

        // 复制属性
        BeanUtils.copyProperties(hotelInformationPO, hotelInformationVO);

        // 将buttonId放入
        hotelInformationVO.setButtonList(buttonList);

        return hotelInformationVO;
    }

    /**
     * 更新酒店信息
     * @param hotelInformationVO 酒店信息封装类
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateHotelInformation(HotelInformationVO hotelInformationVO) {

        // 判断hotelInformationVO是否为空
        if (hotelInformationVO == null)
            throw new RuntimeException(ExceptionConstant.DATA_IS_NOT_NULL_EXCEPTION);

        // 创建vo字段
        HotelInformationPO hotelInformationPO = new HotelInformationPO();

        // 复制属性
        BeanUtils.copyProperties(hotelInformationVO, hotelInformationPO);

        // 拿到当前时间
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Date UpdateTime = calendar.getTime();

        // 更改更新时间
        hotelInformationPO.setUpdateTime(UpdateTime);

        // 更新数据库
        hotelInformationMapper.updateHotelInformation(hotelInformationPO);

    }

    /**
     * 图片上传
     * @param returnPicture 图片信息
     */
    @Override
    public void uploadReturnPicture(MultipartFile returnPicture)  {

        // 判断
        if (returnPicture == null)
            throw new RuntimeException(ExceptionConstant.DATA_IS_NOT_NULL_EXCEPTION);

        // 执行文件上传
        String headerPicture = null;
        try {
            headerPicture = OSSUtils.uploadFileToOss(ossProperties.getEndPoint(),
                    ossProperties.getAccessKeyId(),
                    ossProperties.getAccessKeySecret(),
                    returnPicture.getInputStream(),
                    ossProperties.getBucketName(),
                    ossProperties.getBucketDomain(),
                    returnPicture.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("上传失败");
        }

        // 判断
        if (headerPicture == null)
            throw new RuntimeException("上传失败");

        // 存入数据库
        hotelInformationMapper.updateHeaderPictureById(headerPicture);
    }

}

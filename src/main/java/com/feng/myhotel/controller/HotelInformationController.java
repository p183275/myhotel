package com.feng.myhotel.controller;

import com.feng.myhotel.entities.result.CommonResult;
import com.feng.myhotel.entities.vo.HotelInformationVO;
import com.feng.myhotel.service.HotelInformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author : pcf
 * @date : 2021/11/14 22:08
 */
@RestController
@Api(tags = "酒店信息控制器")
public class HotelInformationController {

    @Autowired
    private HotelInformationService hotelInformationService;

    @PostMapping(value = "/upload/hotel/header/picture")
    @ApiOperation(value = "酒店信息：axios形式上传图片信息", httpMethod = "POST")
    public CommonResult<String> uploadReturnPicture(
            @ApiParam(value = "表单类型的图片信息", name = "returnPicture") @RequestParam("returnPicture") MultipartFile returnPicture) {

        try {
            hotelInformationService.uploadReturnPicture(returnPicture);
            return new CommonResult<>(200, "上传成功");

        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "上传失败");
        }
    }

    @PostMapping(value = "/update/hotel/information")
    @ApiOperation(value = "酒店信息：更新酒店信息", httpMethod = "POST", notes = "请求一定要带token信息")
    public CommonResult<String> updateHotelInformation(
           @ApiParam(name = "hotelInformationVO") @RequestBody HotelInformationVO hotelInformationVO){

        try {
            hotelInformationService.updateHotelInformation(hotelInformationVO);
            return new CommonResult<>(200, "更改完成!!!");

        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "更改失败!!!");
        }

    }

    @PostMapping(value = "/get/hotel/information")
    @ApiOperation(value = "酒店信息：获取酒店信息", httpMethod = "POST", notes = "注意一定要带token！！！")
    public CommonResult<HotelInformationVO> getHotelInformation(
            HttpServletRequest request,
            @ApiParam(value = "id的键值对", name = "map") @RequestBody Map<String, String> map){

        try {
            HotelInformationVO hotelInformation = hotelInformationService.getHotelInformation(request, map);
            return new CommonResult<>(200, "获取信息成功", hotelInformation);

        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "获取信息失败");
        }
    }

}

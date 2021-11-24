package com.feng.myhotel.controller;

import com.feng.myhotel.entities.result.CommonResult;
import com.feng.myhotel.entities.result.ResultWithButtons;
import com.feng.myhotel.entities.vo.RoomReceiveVO;
import com.feng.myhotel.service.QueryInformationService;
import com.feng.myhotel.service.RoomReceiveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author : pcf
 * @date : 2021/11/16 22:43
 */
@RestController
@Api(tags = "接待管理")
public class RoomReceiveController {

    @Autowired
    private QueryInformationService queryInformationService;
    @Autowired
    private RoomReceiveService roomReceiveService;

    @PostMapping(value = "/user/check/out/room/order")
    @ApiOperation(value = "接待管理：退房", httpMethod = "POST", notes = "请求请带token")
    public CommonResult<String> checkOutOrder(
            @ApiParam(value = "客户退房，传一个订单主键的封装类(id字段不是orderId哦)", name = "map", example = "id:1")
            @RequestBody Map<String, Integer> map){

        try {
            roomReceiveService.checkOutRoom(map);
            return new CommonResult<>(200, "成功");

        }catch (Exception exception){
            exception.printStackTrace();
            return new CommonResult<>(400, "失败," + exception.getMessage());
        }
    }

    @PostMapping(value = "/user/receive/room/order")
    @ApiOperation(value = "接待管理：客户住房生成订单", httpMethod = "POST", notes = "请求请带token")
    public CommonResult<String> createOrder(
            @ApiParam(value = "生成订单的封装类", name = "roomReceiveVO")
                @RequestBody RoomReceiveVO roomReceiveVO){

        try {
            roomReceiveService.receiveRoom(roomReceiveVO);
            return new CommonResult<>(200, "成功");

        }catch (Exception exception){
            exception.printStackTrace();
            return new CommonResult<>(400, "失败," + exception.getMessage());
        }
    }

//    @GetMapping(value = "/user/get/receive/button/list")
//    @ApiOperation(value = "拿到button", httpMethod = "GET", notes = "请求请带token")
//    public ResultWithButtons<String> getButtonList(
//            HttpServletRequest request,
//            @ApiParam(value = "菜单id", name = "id", example = "1") @RequestParam("id")Integer id){
//
//        try {
//            List<String> buttonList = queryInformationService.getButtonList(request, id);
//            return new ResultWithButtons<>(200, "成功", null, buttonList);
//
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            return new ResultWithButtons<>(400, "失败" + exception.getMessage());
//        }
//
//    }


}

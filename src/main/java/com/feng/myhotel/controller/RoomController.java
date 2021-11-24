package com.feng.myhotel.controller;

import com.feng.myhotel.entities.po.RoomTypePO;
import com.feng.myhotel.entities.result.CommonResult;
import com.feng.myhotel.entities.result.ResultWithButtons;
import com.feng.myhotel.entities.vo.RoomVO;
import com.feng.myhotel.service.QueryInformationService;
import com.feng.myhotel.service.RoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author : pcf
 * @date : 2021/11/14 23:43
 */
@RestController
@Api(tags = "房间信息控制器")
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private QueryInformationService queryInformationService;

    @PostMapping(value = "/get/room/information")
    @ApiOperation(value = "公用：查询所有房间信息", httpMethod = "POST", notes = "请求请带上token")
    public ResultWithButtons<List<RoomVO>> getAllRoom(HttpServletRequest request,
                                                      @ApiParam(value = "id的键值对", name = "map") @RequestBody Map<String, String> map){
        try {
            List<RoomVO> roomList = roomService.getAllRoom();
            List<String> buttonList = queryInformationService.getButtonList(request, map);
            return new ResultWithButtons<>(200, "查询房间类型信息成功", roomList, buttonList);

        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResultWithButtons<>(400, "拿到房间类型信息失败");
        }
    }

    @PostMapping(value = "/delete/room/information")
    @ApiOperation(value = "房间信息：删除房间", httpMethod = "POST", notes = "请求请带上token")
    public CommonResult<String> deleteRoom(
            @ApiParam(value = "支持批量删除", name = "idListMap", example = "idList:[id数组]") @RequestBody Map<String, List<Integer>> idListMap){
        try {
            roomService.deleteRoomById(idListMap);
            return new CommonResult<>(200, "删除房间成功");

        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "删除房间失败," + exception.getMessage());
        }
    }

    @PostMapping(value = "/insert/room/information")
    @ApiOperation(value = "房间信息：增加房间", httpMethod = "POST", notes = "请求请带上token")
    public CommonResult<String> insertRoom(
            @ApiParam(value = "id可有可无 type（房间类型）可以不用传, typeId一定要传", name = "roomVO" )
                @RequestBody RoomVO roomVO){
        try {
            roomService.insertRoom(roomVO);
            return new CommonResult<>(200, "增加房间成功");

        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "增加房间失败," + exception.getMessage());
        }
    }

    @PostMapping(value = "/update/room/information")
    @ApiOperation(value = "房间信息：修改房间信息", httpMethod = "POST", notes = "请求请带上token")
    public CommonResult<String> updateRoom(
            @ApiParam(value = "除了status和type的所有字段", name = "roomVO") @RequestBody RoomVO roomVO){
        try {
            roomService.updateRoom(roomVO);
            return new CommonResult<>(200, "修改房间成功");

        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "修改房间失败," + exception.getMessage());
        }
    }

    @GetMapping(value = "/get/room/type")
    @ApiOperation(value = "公用：拿到所有房间类型请求", httpMethod = "GET", notes = "请求请带上token")
    public CommonResult<List<RoomTypePO>> getAllRoomType(){
        try {
            List<RoomTypePO> roomTypeList = roomService.getAllRoomType();
            return new CommonResult<>(200, "查询房间类型信息成功", roomTypeList);

        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "拿到房间类型信息失败");
        }
    }

}


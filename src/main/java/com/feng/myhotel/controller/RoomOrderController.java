package com.feng.myhotel.controller;

import com.feng.myhotel.entities.result.CommonResult;
import com.feng.myhotel.entities.result.ResultWithButtons;
import com.feng.myhotel.entities.vo.RoomOrderVO;
import com.feng.myhotel.service.QueryInformationService;
import com.feng.myhotel.service.RoomOrderService;
import com.github.pagehelper.PageInfo;
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
 * @date : 2021/11/15 23:29
 */
@RestController
@Api(tags = "房间订单信息控制器")
public class RoomOrderController {

    @Autowired
    private RoomOrderService roomOrderService;
    @Autowired
    private QueryInformationService queryInformationService;

    @GetMapping(value = "/get/assigned/status/room/order")
    @ApiOperation(value = "公用：拿到指定状态的订单状态的房间订单信息 0--未生效 1--正在生效 2--已结束", httpMethod = "GET")
    public ResultWithButtons<PageInfo<RoomOrderVO>> getAllRoomOrder(
            HttpServletRequest request,
            @ApiParam(value = "每个页面显示的数据", name = "pageSize", example = "1")
                @RequestParam(value = "pageSize", defaultValue = "10", required = false)Integer pageSize,
            @ApiParam(value = "当前页面的页数", name = "pageIndex", example = "1")
                @RequestParam(value = "pageIndex", defaultValue = "1", required = false)Integer pageIndex,
            @ApiParam(value = "状态 0--正在预定的订单 1--正在生效 2--已经结束的订单", example = "0")
                @RequestParam(value = "status", defaultValue = "0", required = false)String status,
            @ApiParam(value = "当前菜单的id", name = "id", example = "1")
                @RequestParam(value = "id")Integer id){

        try {
            PageInfo<RoomOrderVO> roomOrderList = roomOrderService.getAllRoomOrder(pageSize, pageIndex, status);
            List<String> buttonList = queryInformationService.getButtonList(request, id);
            return new ResultWithButtons<>(200, "拿到订单信息成功", roomOrderList, buttonList);

        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResultWithButtons<>(400, "拿到订单信息失败");
        }
    }

    @GetMapping(value = "/get/all/room/order/log/by/page")
    @ApiOperation(value = "订单日志：拿到所有订单", httpMethod = "GET", notes = "请求带上token，两个参数都使用表单格式提交")
    public ResultWithButtons<PageInfo<RoomOrderVO>> getAllRoomOrderByPage(
            HttpServletRequest request,
            @ApiParam(value = "每个页面显示的数据", name = "pageSize", example = "1")
                @RequestParam(value = "pageSize", defaultValue = "10", required = false)Integer pageSize,
            @ApiParam(value = "当前页面的页数", name = "pageIndex", example = "1")
                @RequestParam(value = "pageIndex", defaultValue = "1", required = false)Integer pageIndex,
            @ApiParam(value = "当前菜单的id", name = "id", example = "1")
                @RequestParam(value = "id")Integer id){

        try {
            PageInfo<RoomOrderVO> pageInfo = roomOrderService.getAllRoomOrderByPage(pageSize, pageIndex);
            List<String> buttonList = queryInformationService.getButtonList(request, id);
            return new ResultWithButtons<>(200, "拿到订单信息成功", pageInfo, buttonList);

        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResultWithButtons<>(400, "拿到订单信息失败" + exception.getMessage());
        }
    }

    @PostMapping(value = "/distribute/room/for/order")
    @ApiOperation(value = "预定列表：根据roomId和id(订单的主键,查询操作中有此主键)分配房间", httpMethod = "POST", notes = "请求带token")
    public CommonResult<String> distributeRoom(
            @ApiParam(value = "roomId和id(订单的主键,查询操作中有此主键)的key和value形式", name = "map", example = "roomId:1")
                @RequestBody Map<String, String> map){

        try {
            roomOrderService.distributeRoom(map);
            return new CommonResult<>(200, "修改成功");

        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "修改失败" + exception.getMessage());
        }
    }

    @PostMapping(value = "/delete/room/order")
    @ApiOperation(value = "公用：删除订单", httpMethod = "POST", notes = "请求请带上token")
    public CommonResult<String> deleteRoomOrder(
            @ApiParam(value = "支持批量删除", name = "idListMap", example = "idList:[id数组]")
                @RequestBody Map<String, List<Long>> idListMap){
        try {
            roomOrderService.deleteRoomOrders(idListMap);
            return new CommonResult<>(200, "删除房间成功");

        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "删除房间失败," + exception.getMessage());
        }
    }

}

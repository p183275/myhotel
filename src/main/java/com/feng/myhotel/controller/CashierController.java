package com.feng.myhotel.controller;

import com.feng.myhotel.entities.result.CommonResult;
import com.feng.myhotel.entities.result.ResultWithButtons;
import com.feng.myhotel.entities.vo.CashierPayVO;
import com.feng.myhotel.entities.vo.DepositAndOrderVO;
import com.feng.myhotel.entities.vo.RoomOrderVO;
import com.feng.myhotel.service.CashierService;
import com.feng.myhotel.service.QueryInformationService;
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
 * @date : 2021/11/18 17:12
 */
@RestController
@Api(tags = "收银管理")
public class CashierController {

    @Autowired
    private CashierService cashierService;
    @Autowired
    private QueryInformationService queryInformationService;

    @PostMapping(value = "/user/return/deposit")
    @ApiOperation(value = "押金管理：退还押金操作", httpMethod = "POST")
    public CommonResult<String> giveBackDeposit(
            @ApiParam(value = "封装押金表id的key和value", example = "depositId:1")
                @RequestBody Map<String, Long> map){

        try {
            cashierService.giveBackDeposit(map);
            return new CommonResult<>(200, "退还押金成功");

        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "退还押金失败" + exception.getMessage());
        }
    }

    @PostMapping(value = "/user/create/Deposit")
    @ApiOperation(value = "客房付款：付款+生成押金", httpMethod = "POST")
    public CommonResult<String> createDeposit(
            @ApiParam(value = "押金封装类 参数分别是订单的主键 和 押金的金额")
                @RequestBody CashierPayVO cashierPayVO){

        try {
            cashierService.createDeposit(cashierPayVO);
            return new CommonResult<>(200, "存入押金成功");

        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "存入押金失败");
        }
    }

    @GetMapping(value = "/get/not/pay/room/order")
    @ApiOperation(value = "客房付款：客房付款中拿到未付款的订单", httpMethod = "GET")
    public ResultWithButtons<PageInfo<RoomOrderVO>> getUnPayRoomOrder(
            HttpServletRequest request,
            @ApiParam(value = "每个页面显示的数据", name = "pageSize", example = "1")
                @RequestParam(value = "pageSize", defaultValue = "10", required = false)Integer pageSize,
            @ApiParam(value = "当前页面的页数", name = "pageIndex", example = "1")
                @RequestParam(value = "pageIndex", defaultValue = "1", required = false)Integer pageIndex,
            @ApiParam(value = "状态 0--正在预定的订单 1--正在生效 2--已经结束的订单", example = "0")
                @RequestParam(value = "payStatus", defaultValue = "0", required = false)String payStatus,
            @ApiParam(value = "当前菜单的id", name = "id", example = "1")
                @RequestParam(value = "id")Integer id){

        try {
            PageInfo<RoomOrderVO> roomOrderList = cashierService.getUnPayOrder(pageIndex, pageSize, payStatus);
            List<String> buttonList = queryInformationService.getButtonList(request, id);
            return new ResultWithButtons<>(200, "拿到未付款订单信息成功", roomOrderList, buttonList);

        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResultWithButtons<>(400, "拿到未付款订单信息失败");
        }
    }

    @GetMapping(value = "/get/not/return/deposit")
    @ApiOperation(value = "押金管理：押金管理显示(未退还押金的订单)数据", httpMethod = "GET")
    public ResultWithButtons<PageInfo<DepositAndOrderVO>> getUnDepositRoomOrder(
            HttpServletRequest request,
            @ApiParam(value = "每个页面显示的数据", name = "pageSize", example = "1")
                @RequestParam(value = "pageSize", defaultValue = "10", required = false)Integer pageSize,
            @ApiParam(value = "当前页面的页数", name = "pageIndex", example = "1")
                @RequestParam(value = "pageIndex", defaultValue = "1", required = false)Integer pageIndex,
            @ApiParam(value = "押金状态 0--未退还 1--已退还")
                @RequestParam(value = "status", defaultValue = "0")String status,
            @ApiParam(value = "当前菜单的id", name = "id", example = "1")
                @RequestParam(value = "id")Integer id){

        try {
            PageInfo<DepositAndOrderVO> depositAndOrderVOList = cashierService.getDepositAndOrderVO(pageIndex, pageSize, status);
            List<String> buttonList = queryInformationService.getButtonList(request, id);
            return new ResultWithButtons<>(200, "未退押金订单列表显示成功", depositAndOrderVOList, buttonList);

        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResultWithButtons<>(400, "未退押金订单列表显示失败");
        }
    }

}

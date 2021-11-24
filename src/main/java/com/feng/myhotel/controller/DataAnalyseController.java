package com.feng.myhotel.controller;

import com.feng.myhotel.entities.result.CommonResult;
import com.feng.myhotel.entities.vo.OrderNumberVO;
import com.feng.myhotel.service.DataAnalyseService;
import com.feng.myhotel.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author : pcf
 * @date : 2021/11/21 16:54
 */
@RestController
@Api(tags = "数据分析")
public class DataAnalyseController {

    @Autowired
    private DataAnalyseService dataAnalyseService;
    @Autowired
    private RedisService redisService;

    @GetMapping(value = "/user/get/data/of/order/number")
    @ApiOperation(value = "数据分析：拿到所有订单数量统计信息", httpMethod = "GET")
    public CommonResult<List<OrderNumberVO>> getDataOfOrderNumber(){

        try {
            List<OrderNumberVO> allOrderNumber = dataAnalyseService.getAllOrderNumber();
            return new CommonResult<>(200, "成功", allOrderNumber);

        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "失败");
        }

    }

    @GetMapping(value = "/user/get/data/of/gender/number")
    @ApiOperation(value = "数据分析：拿到性别数量", httpMethod = "GET")
    public CommonResult<Map<Integer, Integer>> getGenderNumberFromRedis(){

        try {
            Map<Integer, Integer> genderNumberFromRedis = dataAnalyseService.getGenderNumberFromRedis();
            return new CommonResult<>(200, "成功", genderNumberFromRedis);

        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "失败");
        }

    }

    @GetMapping(value = "/test/redis/incr")
    @ApiOperation(value = "测试：使得房间订单数量加1", httpMethod = "GET")
    public CommonResult<String> testRedisIncr(){
        try {
            return new CommonResult<>(200, "成功", redisService.orderNumIncr("room_order_number").toString());
        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "失败" + exception.getMessage());
        }
    }

    @GetMapping(value = "/test/redis/gender/incr/{gender}")
    @ApiOperation(value = "测试：链接最后一位为变量  为0则女性数量增加 1-男性别增加", httpMethod = "GET")
    public CommonResult<String> testGenderIncr(@PathVariable("gender") Integer gender){
        try {
            return new CommonResult<>(200, "成功", redisService.genderIncr(gender).toString());
        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "失败" + exception.getMessage());
        }
    }

}

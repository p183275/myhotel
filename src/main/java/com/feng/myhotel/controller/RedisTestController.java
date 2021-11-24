package com.feng.myhotel.controller;

import com.feng.myhotel.entities.result.CommonResult;
import com.feng.myhotel.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : pcf
 * @date : 2021/11/21 15:59
 */
@Deprecated
//@RestController
public class RedisTestController {

    //@Autowired
    private RedisService redisService;

    //@GetMapping("/test/redis/incr")
    public CommonResult<String> testRedisIncr(){
        try {
            return new CommonResult<>(200, "成功", redisService.orderNumIncr("room_order_number").toString());
        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "失败" + exception.getMessage());
        }
    }

}

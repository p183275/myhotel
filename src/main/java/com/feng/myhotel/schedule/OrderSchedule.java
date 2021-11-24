package com.feng.myhotel.schedule;

import com.feng.myhotel.entities.vo.OrderNumberVO;
import com.feng.myhotel.service.DataAnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author : pcf
 * @date : 2021/11/21 16:21
 */
@Component
public class OrderSchedule {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private DataAnalyseService dataAnalyseService;

    // 在一个特定的时间执行这个方法
    // cron表达式
    // 秒 分 时 日 月 周
    @Scheduled(cron = "0 58 23 * * 0-7")
    public void insertOrderNumberInDb() {

        // 定义建
        String key = "room_order_number";

        // 对redis进行操作
        Long roomOrderNumber = redisTemplate.opsForValue().increment(key);

        // 创建订单数量对象
        OrderNumberVO orderNumberVO = new OrderNumberVO();

        // 放入信息
        orderNumberVO.setRoomOrderNumber(roomOrderNumber);

        // 拿到当前时间
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Date time = calendar.getTime();

        orderNumberVO.setCreateDate(time);

        // 执行存库操作
        dataAnalyseService.insertOrderNumber(orderNumberVO);

        // 将redis中的数据清为0
        Integer data = 0;
        redisTemplate.opsForValue().set(key, data);
    }

}

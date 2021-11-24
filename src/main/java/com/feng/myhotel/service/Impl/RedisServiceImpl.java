package com.feng.myhotel.service.Impl;

import com.feng.myhotel.entities.constant.RedisConstant;
import com.feng.myhotel.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author : pcf
 * @date : 2021/11/21 15:52
 */
@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 是记录自增
     * @param key 主键
     * @return 自增的数量
     */
    @Override
    public Long orderNumIncr(String key) {

        // 自增
        Long increment = redisTemplate.opsForValue().increment(key);

        log.info("******数量 " + increment);

        return increment;
    }

    /**
     * gender -0 女性数量加一 -1男性数量加1
     * @param gender 0 or 1
     */
    @Override
    public Long genderIncr(Integer gender) {

        // 动态生成key
        String key = RedisConstant.GENDER_NUMBER_KEY + gender;

        // 自增
        return redisTemplate.opsForValue().increment(key);
    }
}

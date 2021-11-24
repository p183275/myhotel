package com.feng.myhotel.service.Impl;

import com.feng.myhotel.entities.constant.ExceptionConstant;
import com.feng.myhotel.entities.constant.RedisConstant;
import com.feng.myhotel.entities.vo.OrderNumberVO;
import com.feng.myhotel.mapper.OrderNumberMapper;
import com.feng.myhotel.service.DataAnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : pcf
 * @date : 2021/11/21 16:27
 */
@Service
public class DataAnalyseServiceImpl implements DataAnalyseService {

    @Autowired
    private OrderNumberMapper orderNumberMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertOrderNumber(OrderNumberVO orderNumberVO) {

        // 判断数据是否为空
        if (orderNumberVO == null)
            throw new RuntimeException(ExceptionConstant.DATA_IS_NOT_NULL_EXCEPTION);

        // 存入数据库
        orderNumberMapper.insertOrderNumber(orderNumberVO);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderNumberVO> getAllOrderNumber() {
        return orderNumberMapper.getAllOrderNumber();
    }

    /**
     * 从redis中获取性别数量
     * @return 性别map
     */
    @Override
    public Map<Integer, Integer> getGenderNumberFromRedis() {

        // 拿到女性的数量
        Integer womenNumber = (Integer) redisTemplate.opsForValue().get(RedisConstant.GENDER_NUMBER_KEY + 0);

        // 拿到男性的数量
        Integer menNumber = (Integer) redisTemplate.opsForValue().get(RedisConstant.GENDER_NUMBER_KEY + 1);

        // 创建map封装，放入数据
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, womenNumber);
        map.put(1, menNumber);

        return map;
    }
}

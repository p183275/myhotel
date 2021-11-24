package com.feng.myhotel.service;

/**
 * @author : pcf
 * @date : 2021/11/21 15:52
 */
public interface RedisService {

    /**
     * 是记录自增
     * @param key 主键
     * @return 自增的数量
     */
    Long orderNumIncr(String key);

    /**
     * gender -0 女性数量加一 -1男性数量加1
     * @param gender 0 or 1
     */
    Long genderIncr(Integer gender);

}

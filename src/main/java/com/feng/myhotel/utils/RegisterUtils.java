package com.feng.myhotel.utils;

import cn.hutool.core.util.NumberUtil;

/**
 * 注册工具类
 * @author : pcf
 * @date : 2021/11/11 18:32
 */
public class RegisterUtils {

    /**
     * 生成验证码
     * @return 返回生成的验证码
     */
    public static String getCheckCode() {

        // 使用糊涂工具生成验证码
        int[] numbers = NumberUtil.generateRandomNumber(1000, 9999, 1);
        return numbers[0] + "";
    }

}

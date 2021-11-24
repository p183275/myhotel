package com.feng.myhotel.entities.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author : pcf
 * @date : 2021/11/11 18:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommonResult<T>{

    // 返回代码
    private Integer code;

    // 返回的消息
    private String message;

    // 返回的数据
    private T data;

    public CommonResult(Integer code, String message){

        this(code, message, null);
    }

}

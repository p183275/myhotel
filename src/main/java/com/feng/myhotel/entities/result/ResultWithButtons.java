package com.feng.myhotel.entities.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author : pcf
 * @date : 2021/11/16 20:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResultWithButtons<T> {

    // 返回代码
    private Integer code;

    // 返回的消息
    private String message;

    // 返回的数据
    private T data;

    private List<String> buttonList;

    public ResultWithButtons(Integer code, String message){

        this(code, message, null);
    }

    public ResultWithButtons(Integer code, String message, T data){

        this(code, message, data, null);
    }

}

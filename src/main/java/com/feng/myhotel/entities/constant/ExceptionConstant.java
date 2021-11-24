package com.feng.myhotel.entities.constant;

/**
 * @author : pcf
 * @date : 2021/11/12 21:24
 */
public class ExceptionConstant {

    public static final String DATA_IS_NOT_NULL_EXCEPTION = "信息不能为空!";
    public static final String TOKEN_IS_NOT_NULL_EXCEPTION = "TOKEN信息不能为空!";

    public static final String LOGIN_ACCOUNT_HAVE_NONE_MESSAGE = "无此账号信息!";
    public static final String ROOM_HAVE_NONE_MESSAGE = "无此房间信息!";

    public static final String PASSWORD_INPUT_ERROR_EXCEPTION = "密码输入错误!";

    public static final String PHONE_INPUT_ERROR_EXCEPTION = "未查询到此电话号码绑定的用户，请检查电话号码是否输入正确!";

    public static final String CHECK_CODE_EXPIRE_EXCEPTION = "验证码过期，请重新发送!";
    public static final String CHECK_CODE_IS_NOT_NULL = "验证码不能为空!";
    public static final String CHECK_CODE_INPUT_ERROR_EXCEPTION = "验证码输入错误，请检查手机号码或者验证码";

}

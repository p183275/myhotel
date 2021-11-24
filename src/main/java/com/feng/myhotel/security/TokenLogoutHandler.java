package com.feng.myhotel.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feng.myhotel.entities.constant.LoginConstant;
import com.feng.myhotel.entities.result.CommonResult;
import com.feng.myhotel.utils.JWTUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登出时候的处理
 * @author : pcf
 * @date : 2021/11/11 18:37
 */
public class TokenLogoutHandler implements LogoutHandler {

    private RedisTemplate redisTemplate;

    public TokenLogoutHandler(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        // 从请求头中获取token
        String token = request.getHeader("token");

        // 判断如果token不为空，先移除token后再从redis中删除token
        if (token != null){

            // 从token中获取用户名
            DecodedJWT verify = JWTUtils.verify(token);
            String loginAccount = verify.getClaim("loginAccount").asString();

            // 从redis中删除token
            redisTemplate.delete(loginAccount + LoginConstant.VALUE_CREATE_TOKEN);

            // 从redis中删除authList
            redisTemplate.delete(loginAccount + LoginConstant.VALUE_CREATE_AUTH_LIST);

        }

        // 创建返回体
        CommonResult<Object> result = new CommonResult<>(200, "登出成功！！！");

        try {
            // 将返回体转化为json字符串
            String json = new ObjectMapper().writeValueAsString(result);

            // 设置编码类型
            response.setContentType("application/json;charset=UTF-8");

            // 返回
            response.getWriter().write(json);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

package com.feng.myhotel.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feng.myhotel.entities.constant.LoginConstant;
import com.feng.myhotel.entities.result.CommonResult;
import com.feng.myhotel.entities.vo.UserVO;
import com.feng.myhotel.security.SecurityUser;
import com.feng.myhotel.utils.JWTUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录认证方法
 * @author : pcf
 * @date : 2021/11/11 18:51
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private RedisTemplate redisTemplate;

    private AuthenticationManager authenticationManager;

    public TokenLoginFilter(AuthenticationManager authenticationManager, RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.authenticationManager = authenticationManager;
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/user/login", "POST"));
    }

    // 认证方法
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        // 获取表单提交的登录账号
        try {
            UserVO userVO = new ObjectMapper().readValue(request.getInputStream(), UserVO.class);
            // 暂时把身份认证信息放入登录账号中
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userVO.getLoginAccount() , userVO.getPassword(),
                    new ArrayList<>()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("未获取到登录信息");
        }

    }

    // 认证成功方法
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        // 获取认证成功后的用户信息
        SecurityUser user = (SecurityUser) authResult.getPrincipal();

        // 根据用户信息生成token
        Map<String, String> map = new HashMap<>();
        map.put("loginAccount", user.getUsername());
        map.put("userId", String.valueOf(user.getLoginUserVO().getId()));
        map.put("roleId", String.valueOf(user.getCurrentUserInfo().getRoleId()));

        // 参数放入map中，设置过期时间1天
        String token = JWTUtils.getToken(map, 1);

        // 将用户产生的token存入redis
        redisTemplate.opsForValue().set(user.getUsername() + LoginConstant.VALUE_CREATE_TOKEN, token);

        // 把用户名称及用户权限信息放入redis
        redisTemplate.opsForValue().set(user.getUsername() + LoginConstant.VALUE_CREATE_AUTH_LIST, user.getPermissionValueList(), Duration.ofDays(1));

        // 将token返回前端
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);

        // 返回体
        CommonResult<Map<String, String>> result = new CommonResult<>(200, "登录成功", tokenMap);

        // 将其转为json
        String json = new ObjectMapper().writeValueAsString(result);

        // 设置编码类型
        response.setContentType("application/json;charset=UTF-8");

        // 返回前端
        response.getWriter().write(json);

    }

    // 认证失败方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {

        // 返回体
        CommonResult<Object> result = new CommonResult<>(400, "账号或密码错误，请重新尝试");

        // 转为json
        String json = new ObjectMapper().writeValueAsString(result);

        // 设置编码类型
        response.setContentType("application/json;charset=UTF-8");

        // 返回前端
        response.getWriter().write(json);
    }
}

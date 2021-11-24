package com.feng.myhotel.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.feng.myhotel.entities.constant.LoginConstant;
import com.feng.myhotel.utils.JWTUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 授权方法
 * @author : pcf
 * @date : 2021/11/11 18:54
 */
public class TokenAuthFilter extends BasicAuthenticationFilter {

    private RedisTemplate redisTemplate;

    public TokenAuthFilter(AuthenticationManager authenticationManager, RedisTemplate redisTemplate) {
        super(authenticationManager);
        this.redisTemplate = redisTemplate;
    }

    // 过滤规则
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 获取当前用户认证成功后的权限信息
        UsernamePasswordAuthenticationToken authRequest = getAuthentication(request);

        // 如果有权限信息放入权限上下文中
        if (authRequest != null) {
            SecurityContextHolder.getContext().setAuthentication(authRequest);
        }

        // 过滤器放行
        chain.doFilter(request, response);
    }

    /**
     * 拿到权限列表
     * @param request 请求
     * @return 使用用户信息编写的token
     */
    public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // 从header中获取token
        String token = request.getHeader("token");

        if (token != null) {

            // 从token中获取用户信息
            DecodedJWT verify = JWTUtils.verify(token);
            String loginAccount = verify.getClaim("loginAccount").asString();

            // 从redis中获取权限列表
            List<String> permissionValueList = (List<String>) redisTemplate.opsForValue()
                    .get(loginAccount + LoginConstant.VALUE_CREATE_AUTH_LIST);

            // 更换permissionValueList中的数据类型
            Collection<GrantedAuthority> authorities = new ArrayList<>();

            for (String permission : permissionValueList) {
                // 将String类型进行转换
                SimpleGrantedAuthority auth = new SimpleGrantedAuthority(permission);
                authorities.add(auth);
            }

            return new UsernamePasswordAuthenticationToken(loginAccount, token, authorities);
        }
        return null;
    }

}

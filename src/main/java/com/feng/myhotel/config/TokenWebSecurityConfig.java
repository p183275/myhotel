package com.feng.myhotel.config;

import com.feng.myhotel.filter.TokenAuthFilter;
import com.feng.myhotel.filter.TokenLoginFilter;
import com.feng.myhotel.security.DefaultPasswordEncoder;
import com.feng.myhotel.security.TokenLogoutHandler;
import com.feng.myhotel.security.UnauthEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author : pcf
 * @date : 2021/11/11 19:39
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private RedisTemplate redisTemplate;
    private UserDetailsService userDetailsService;
    private DefaultPasswordEncoder passwordEncoder;

    @Autowired
    public TokenWebSecurityConfig(RedisTemplate redisTemplate, UserDetailsService userDetailsService,
                                  DefaultPasswordEncoder passwordEncoder) {

        this.redisTemplate = redisTemplate;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 在其中设置自定义处理和自定义过滤器
     *
     * @param http 参数
     * @throws Exception 所有异常
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(new UnauthEntryPoint()) // 无权访问
                .and().csrf().disable() // 关闭跨站请求伪造防护
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/user/logout") // 退出url
                .addLogoutHandler(new TokenLogoutHandler(redisTemplate))
                .and().addFilter(new TokenLoginFilter(authenticationManager(), redisTemplate))
                .addFilter(new TokenAuthFilter(authenticationManager(), redisTemplate))
                .httpBasic();
    }

//    @Bean
//    public BCryptPasswordEncoder getPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    // 不参与认证的路径。可以直接访问
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/user/get/check/code", "/user/update/by/password",
                "/user/update/by/phone", "/user/register", "/get/all/role",
                "/get/all/auth", "/user/login/by/phone",
                "/swagger-ui.html", "/v2/api-docs",
                "/swagger-resources/configuration/ui",
                "/swagger-resources",
                "/swagger-resources/configuration/security",
                "/swagger-ui.html","/test/redis/incr","/test/redis/gender/incr/*",
                "/webjars/springfox-swagger-ui/swagger-ui.css",
                "/webjars/springfox-swagger-ui/springfox.css",
                "/webjars/springfox-swagger-ui/springfox.js",
                "/webjars/springfox-swagger-ui/swagger-ui-standalone-preset.js",
                "/webjars/springfox-swagger-ui/swagger-ui-bundle.js",
                "/webjars/springfox-swagger-ui/swagger-ui.css",
                "/webjars/springfox-swagger-ui/springfox.css");
    }

}

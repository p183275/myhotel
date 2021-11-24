package com.feng.myhotel.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/**
 * swagger的配置类
 * @author : pcf
 * @date : 2021/11/11 18:29
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                //.apiInfo(apiInfo())
                //.select()
                //RequestHandlerSelectors配置要扫描接口的方式
                //basePackage();指定要扫描的包
                //any();扫描任何包
                //none();都不扫描
                //withClassAnnotation();扫描类上的注解
                //withMethodAnnotation();扫描方法上的注解
                //.apis(RequestHandlerSelectors.withClassAnnotation(GetMapping.class))
                //过滤
                //ant();根据路径过滤
                //.paths(PathSelectors.ant("com.feng.**"))
                //.build();
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    //配置swagger信息
    public ApiInfo apiInfo() {
        //作者信息
        Contact DEFAULT_CONTACT = new Contact("庞", "", "");
        return new ApiInfo("feng sir's dirary文档",
                "纵有疾风起",
                "1.0",
                "urn:tos",
                DEFAULT_CONTACT,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());

    }

}

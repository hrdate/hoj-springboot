package com.hrdate.oj.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Collections;

/**
 * Knife4j配置类
 *
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                //.protocols(Collections.singleton("http"))
                .host("127.0.0.1:8080")
                .apiInfo(apiInfo())
                .select()
                // 扫描controller层
                .apis(RequestHandlerSelectors.basePackage("com.hrdate.oj.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("my-onlie-judge-api文档")
                .description("springboot+vue开发的项目")
                .termsOfServiceUrl("127.0.0.1:8080")
                .contact(new Contact("huangrendi", "https://github.com/hrdate", "535523596@qq.com"))
                .version("1.0")
                .build();
    }

}

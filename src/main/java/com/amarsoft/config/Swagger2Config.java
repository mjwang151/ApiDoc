package com.amarsoft.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @org.springframework.context.annotation.Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.amarsoft.controller"))
                .paths(PathSelectors.any())//所有的路径
                .build().apiInfo(new ApiInfoBuilder() //apinfo配置那个网站的基本信息swagger-ui
                        .title("Swagger工具页面")
                        .description("接口调用及文档生成小工具")
                        .version("1.0")
                        .contact(new Contact("数据资产平台快速跳转","http://192.168.61.240/DtAdmin/#/login","")) //联系人信息
//                        .license("The Apache License")
//                        .licenseUrl("http://www.baidu.com") //协议地址
                        .build());
    }




}

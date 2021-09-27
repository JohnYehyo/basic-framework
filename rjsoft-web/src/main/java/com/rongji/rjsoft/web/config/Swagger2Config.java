package com.rongji.rjsoft.web.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
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

/**
 * @description: swagger配置
 * @author: JohnYehyo
 * @create: 2021-04-25 19:39:21
 */
@Configuration
@EnableSwagger2WebMvc
@EnableKnife4j
public class Swagger2Config {

    @Value("${swagger.enable:false}")
    private boolean swaggerEnable;

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //分组名称
                .groupName("JohnYehyo")
                .enable(swaggerEnable)
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.rongji.rjsoft.web.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("后台管理系统")
                .description("后台管理系统接口文档")
                .contact(contactInfo())
                .version("1.0")
                .build();
    }

    private Contact contactInfo(){
        return new Contact("JohnYehyo", "https://github.com/JohnYehyo", "836524969@qq.com");
    }
}

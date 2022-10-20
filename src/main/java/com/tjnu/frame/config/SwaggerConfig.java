package com.tjnu.frame.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket(Environment environment){

        //设置要显示的Swagger环境
        Profiles profiles = Profiles.of("dev","test");

        //获取项目的环境：
        //通过 environment.acceptsProfiles判断是否处于自己设定的环境当中
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("接口文档")
                .enable(flag)   //enable判断是否启动Swagger，如果为false，则Swagger不能在浏览器中使用
                .select()

                //RequestHandlerSelectors，配置要扫描接口的方式
                //basePackage:指定要扫描的包
                //any:扫描全部
                //none:不扫描
                //withClassAnnotation:扫描类上的注解，参数是一个注解的反射对象
                //withMethodAnnotation:扫描方法上的注解

                .apis(RequestHandlerSelectors.basePackage("com.tjnu"))
                //过滤路径
                //.paths(PathSelectors.ant("/**"))
                .build();   //工厂模式
    }

    //配置Swagger信息=apiInfo
    private ApiInfo apiInfo(){

        //作者信息
        Contact DEFAULT_CONTACT = new Contact("徐雨柔的毕设项目", "#", "616316004@qq.com");

        return new ApiInfo(
                "万卷书堂点评项目的SwaggerAPI文档",
                "天津师范大学18级中软2班徐雨柔制作的接口文档，负责交付前端的接口文档页面",
                "1.0",
                "http://www.rechiard.site",
                DEFAULT_CONTACT,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());

    }
}

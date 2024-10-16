package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@EnableSwagger2
@Configuration
public class Swagger2Config {
    /**
     * createDocket方法用于返回一个Docket对象 (Swagger2的核心对象)
     * @Bean 注解专门用配置类中注册方法的返回对象到IOC容器
     */
    @Bean
    public Docket createDocket () {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.example.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(setParameters());
    }

    /**
     * 返回一个ApiInfo对象
     * 可以在这个ApiInfo对象中设置接口文档的详细信息
     */
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("咚咚审批系统接口文档")
                .description("咚咚审批系统接口文档详细描述")
                .version("1.0")
                // 用户配置api服务使用的协议。如果配置的话应该是一个地址。
                .termsOfServiceUrl("/api/doc")
                .contact(new Contact("作者名","作者的网站","作者的邮箱"))
                .build();
    }

    private List<Parameter> setParameters() {
        List<Parameter> list = new ArrayList<>();
        ParameterBuilder builder = new ParameterBuilder();
        builder.name("Auth").description("请在这里携带token令牌").required(false)
                .modelRef(new ModelRef("string")).parameterType("header");
        list.add(builder.build());
        return list;
    }

}

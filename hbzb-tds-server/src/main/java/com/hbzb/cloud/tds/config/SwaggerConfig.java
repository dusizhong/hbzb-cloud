package com.hbzb.cloud.tds.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
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

/**
 * Swagger配置
 * http://192.168.33.200:8000/api/v1/tds/swagger-ui.html
 * * http://192.168.33.200:8000/api/v1/tds/doc.html
 * created by dusizhong at 2020.01.14
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {

        //Adding Header Authorization
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name("Authorization").modelRef(new ModelRef("String"))
                .parameterType("header").defaultValue("Bearer ")
                .required(true).build();
        java.util.List<Parameter> parameters = new ArrayList<>();
        parameters.add(parameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hbzb.cloud.tds"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .globalOperationParameters(parameters)
                .apiInfo(apiInfo());
    }

    // Add api info
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("HBZB招标人系统接口文档").version("1.0")
                .description("HBZB招标人系统接口文档").contact(new Contact("dusizhong","",""))
                .build();
    }
}
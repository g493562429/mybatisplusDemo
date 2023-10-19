package com.gn.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * swaggerUI接口访问地址： http://localhost:8081/doc.html 或 http://localhost:8081/swagger-ui.html
 */
@Configuration
@Profile({"localhost", "dev", "stg"})
public class Swagger3Config {

    @Bean
    public Docket controllerApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(buildApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.gn.demo.controller"))
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();

    }

    /**
     * 构造ApiInfoBuilder对象
     * 访问地址: http://项目实际地址/swagger-ui/index.html
     * @return ApiInfo
     */
    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .title("gn测试平台")
                .description("用于测试小demo用")
                .version("1.0.0")
                .build();
    }
}

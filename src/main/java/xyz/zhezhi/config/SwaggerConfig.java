package xyz.zhezhi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
/**
  * @author zhezhi
  * @className: SwaggerConfig
  * @description: Swagger配置类
  * @date 2021/8/17 下午9:19
  * @version：1.0
  */
@Configuration
@EnableOpenApi
public class SwaggerConfig  {
    @Bean
    public Docket docket(Environment environment) {
        Profiles of = Profiles.of("dev", "test");
        boolean isDev = environment.acceptsProfiles(of);
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .enable(isDev)
                .select()
                .apis(RequestHandlerSelectors.basePackage("xyz.zhezhi.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("blog项目接口文档")
                .description("blog项目描述")
                .contact(new Contact("zhezhi", "www.zhezhi.xyz", "2544438809@qq.com"))
                .version("1.0")
                .build();
    }
}

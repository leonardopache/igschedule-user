package com.pache.igscheduleuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@SpringBootApplication
@EnableSwagger2
public class IgscheduleuserApplication {

	public static void main(String[] args) {
		SpringApplication.run(IgscheduleuserApplication.class, args);
	}
	
	@Bean
    public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("com.pache.igscheduleuser.controller"))
                .paths(PathSelectors.regex("/api.*"))
                .build().apiInfo(apiInfo());
    }
	
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Spring REST with Swagger")
            .description("Spring REST with Swagger")
            .contact("Leonardo Pache")
            .build();
    }

}

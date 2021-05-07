package com.Teknisi.config;

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
@EnableSwagger2
public class TeknisiConfig {
	
	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_12)
				.apiInfo(apiEndInfo())
				.select()
				.apis(RequestHandlerSelectors
						.basePackage("com.Teknisi.controller"))
				.paths(PathSelectors.regex("/.*"))
				.build()
				;
	}

	private ApiInfo apiEndInfo() {
		return new ApiInfoBuilder().title("Spring Boot Teknisi")
			    .description("Teknisi Swagger")
			    .build()
			    ;
	}
	
}

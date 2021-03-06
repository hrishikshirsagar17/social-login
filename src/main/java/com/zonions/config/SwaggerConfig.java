package com.zonions.config;


import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage("com.zonions")).paths(PathSelectors.any()).build()
        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfo("Zonions API", "Deals with management of restaurants", "1.0",
        "Open Source API",
        new Contact("Zonions", "http://localhost:8080/zonions/restaurants", "zonions@food.com"),
        "Copyrights belongs to zonions", "https://localhost:8080/zonions/restaurants",
        Collections.emptyList());
  }
}

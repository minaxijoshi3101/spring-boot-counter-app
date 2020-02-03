package com.enderculha.apsisassignment.configuration;

import com.enderculha.apsisassignment.repository.CounterHashMapRepository;
import com.enderculha.apsisassignment.repository.CounterRepository;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
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
public class ServiceConfiguration {

  @Bean
  public CounterRepository counterRepository() {
    return new CounterHashMapRepository(new ConcurrentHashMap<>());
  }

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .useDefaultResponseMessages(false)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.enderculha.apsisassignment"))
        .paths(PathSelectors.any())
        .build().apiInfo(new ApiInfo("Basic Counter API", "Documentation automatically generated", "1.0", null,
            new Contact("Ender", "https://github.com/ApsisRecruitment/solution-enderculha", "enderculha@gmail.com"),
            null, null, Collections.emptyList()));
  }

}

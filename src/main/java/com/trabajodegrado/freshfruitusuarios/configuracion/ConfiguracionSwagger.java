package com.trabajodegrado.freshfruitusuarios.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class ConfiguracionSwagger {
    // Documentacion de web services,
    // http://localhost:8090/freshfruitusuarios/api/swagger-ui.html
    @Bean
    public Docket api() {
	return new Docket(DocumentationType.SWAGGER_2)
		.select()								  
		.apis(RequestHandlerSelectors.basePackage("com.trabajodegrado.freshfruitusuarios.controladores"))
		.paths(PathSelectors.any()).build();
    }

}

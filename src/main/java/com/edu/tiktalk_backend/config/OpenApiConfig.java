package com.edu.tiktalk_backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Value("${app.base-swagger-url}")
    private String baseSwaggerUrl;
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Tiktalk API")
                        .version("1.0")
                        .description("API description"))
                .addServersItem(new Server().url(baseSwaggerUrl));
    }
}

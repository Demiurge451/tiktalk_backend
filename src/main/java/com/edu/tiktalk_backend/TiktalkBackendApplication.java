package com.edu.tiktalk_backend;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@SecurityScheme(
		name = "Keycloak"
		, openIdConnectUrl = "http://localhost:8180/realms/tiktalk-realm/.well-known/openid-configuration"
		, scheme = "bearer"
		, type = SecuritySchemeType.OPENIDCONNECT
		, in = SecuritySchemeIn.HEADER
)
public class TiktalkBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(TiktalkBackendApplication.class, args);
	}
}

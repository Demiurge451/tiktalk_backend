package com.edu.tiktalk_backend.config;

import com.edu.tiktalk_backend.dto.request.PersonRequest;
import com.edu.tiktalk_backend.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KeycloakConfig {
    @Value("${spring.keycloak.server-url}")
    private String serverUrl;

    @Value("${spring.keycloak.realm}")
    private String realm;

    @Value("${spring.keycloak.client-id}")
    private String clientId;

    @Value("${spring.keycloak.username}")
    private String username;

    @Value("${spring.keycloak.password}")
    private String password;

    @Value("${spring.keycloak.grant-type}")
    private String grantType;

    @Bean
    public Keycloak getInstance(){
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(grantType)
                .username(username)
                .password(password)
                .clientId(clientId)
                .build();
    }
}

package com.example.keycloak.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakClientConfig {
    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.auth-server-url}")
    private String authUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.credentials.secret}")
    private String secret;

    @Bean
    public Keycloak keyCloak() {
        return KeycloakBuilder.builder()
                .clientId("admin-cli")
//                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .realm("master")
                .serverUrl(authUrl)
//                .clientSecret(secret)
                .username("admin")
                .password("admin_password")
                .build();
    }
}

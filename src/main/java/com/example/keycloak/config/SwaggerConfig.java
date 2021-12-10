package com.example.keycloak.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableOpenApi
//@Import(SpringDataRestConfiguration.class)
public class SwaggerConfig {
    @Value("${keycloak.auth-server-url}")
    private String authUrl;
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Arrays.asList(securityScheme()))
                .securityContexts(Arrays.asList(securityContext()))
                ;
    }
    private OAuth securityScheme() {
        return new OAuthBuilder()
                .name("spring_oauth")
                .grantTypes(grantTypes())
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Arrays.asList(new SecurityReference("spring_oauth", new AuthorizationScope[] {})))
                .forPaths(PathSelectors.regex("/api.*"))
                .build();
    }

    private List<GrantType> grantTypes() {
        GrantType grantType = new ClientCredentialsGrant(authUrl);
        return Arrays.asList(grantType);
    }
}

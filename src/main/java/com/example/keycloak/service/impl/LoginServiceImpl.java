package com.example.keycloak.service.impl;

import com.example.keycloak.dto.request.LoginRequestDto;
import com.example.keycloak.dto.request.LoginResponceDto;
import com.example.keycloak.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
        @Value("${app.keycloak.login-url}")
        private String loginUrl;
        @Value("${keycloak.credentials.secret}")
        private String clientSecret;
        @Value("${app.keycloak.grant-type}")
        private String grantType;
        @Value("${keycloak.resource}")
        private String clientId;

        private  final RestTemplate restTemplate;

        public ResponseEntity<LoginResponceDto> login (LoginRequestDto request)  {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("username", request.getUsername());
            map.add("password", request.getPassword());
            map.add("client_id", clientId);
            map.add("client_secret", clientSecret);
            map.add("grant_type", grantType);

            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
            ResponseEntity<LoginResponceDto> loginResponse = restTemplate.postForEntity(loginUrl, httpEntity, LoginResponceDto.class);

            return ResponseEntity.status(200).body(loginResponse.getBody());

        }
}

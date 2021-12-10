package com.example.keycloak.service;

import com.example.keycloak.dto.request.LoginRequestDto;
import com.example.keycloak.dto.request.LoginResponceDto;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    ResponseEntity<LoginResponceDto> login (LoginRequestDto request);
}

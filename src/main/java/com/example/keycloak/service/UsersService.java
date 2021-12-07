package com.example.keycloak.service;

import com.example.keycloak.dto.request.AddUserRequestDto;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UsersService {
    void logout(String name);
    List<UserRepresentation> getUsers();
    ResponseEntity addUser(AddUserRequestDto dto);
    ResponseEntity getUserDOB(String name);
}

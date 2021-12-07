package com.example.keycloak.service.impl;

import com.example.keycloak.converter.AddUserDtoToUserRepresentationConverter;
import com.example.keycloak.dto.request.AddUserRequestDto;
import com.example.keycloak.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UsersService {

    @Value("${keycloak.realm}")
    private String realm;

    private final Keycloak keycloak;

    @Override
    public void logout(String name) {
        keycloak.realm(realm).users().get(name).logout();
    }

    @Override
    public List<UserRepresentation> getUsers() {
        return null;
    }

    @Override
    public ResponseEntity addUser(AddUserRequestDto dto) {
        UserRepresentation userRepresentation = AddUserDtoToUserRepresentationConverter.convert(dto);
        if (keycloak.realm(realm).users().create(userRepresentation).getStatusInfo().toEnum() != Response.Status.OK)
            return ResponseEntity.ok("New user created ");
        else
            return ResponseEntity.ok("Failed to add  user");

    }

    @Override
    public ResponseEntity getUserDOB(String name) {
        System.out.println("Token " + keycloak.tokenManager().getAccessTokenString());
        UserRepresentation userRepresentation = keycloak.realm(realm).users().get(name).toRepresentation();
        return ResponseEntity.ok(name + " dob is " + userRepresentation.getAttributes().get("dob").get(0));
    }
//    Token eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJIYmpxdjVKWVFxSTZDLUFxT3drVEZrTG1uNTJXSnliNjlXOTlFajBZT0hBIn0.eyJleHAiOjE2Mzg3OTEwMzcsImlhdCI6MTYzODc5MDczNywianRpIjoiNzNkNmIwMmItMzk3ZS00ZmFmLTljNDQtOWZmMDQwMGZlOGZlIiwiaXNzIjoiaHR0cDovLzEyNy4wLjAuMTo4NDg0L2F1dGgvcmVhbG1zL2tleWNsb2FrX2RlbW8iLCJhdWQiOiJyZWFsbS1tYW5hZ2VtZW50Iiwic3ViIjoiMzc0MDllZTQtYzc3MS00ZjFjLTliYjYtODUyNzg0MDNlYzA4IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZGVtb19jbGllbnQiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIkFETUlOIiwiVVNFUiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7InJlYWxtLW1hbmFnZW1lbnQiOnsicm9sZXMiOlsibWFuYWdlLXVzZXJzIiwidmlldy11c2VycyIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS1ncm91cHMiLCJxdWVyeS11c2VycyJdfSwiZGVtb19jbGllbnQiOnsicm9sZXMiOlsiVVNFUiJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiY2xpZW50SWQiOiJkZW1vX2NsaWVudCIsImNsaWVudEhvc3QiOiIxNzIuMTguMC4xIiwicHJlZmVycmVkX3VzZXJuYW1lIjoic2VydmljZS1hY2NvdW50LWRlbW9fY2xpZW50IiwiY2xpZW50QWRkcmVzcyI6IjE3Mi4xOC4wLjEifQ.ZBm4BA0A3M9QvI-iLmh9wP5s2Bof5wZPAkO_Jfx7AEuxutB4MF5k5UL150XZnqa0uJ5wzkglOC8qOhqxUTJNUILOPEXniPV9BN-r_RApzeF13aZBVSKniFelTG_YvCNEnT9fURfctCegI38RJME8OIrWJPHda2l1m2dQa2HW1OJh-yQdX4u2vtXdeIVNVm3ySWjdcS9Sa6ikCvF3CY06uOXB1LZ5mLxqpnQU-0nx5G3kVCsGZt5hYRs9YWjJdAoxO-aVCzhHZ6lvqJivtX3_gV9RF895dsovyGcgQjTjP7YT9q7XkT6G6Egudi97Y6uvb7zFGVEw7ESrhPk8lwXzuQ

}

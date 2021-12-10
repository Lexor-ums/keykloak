package com.example.keycloak.converter;

import com.example.keycloak.dto.request.AddUserRequestDto;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Collections;

public class AddUserDtoToUserRepresentationConverter {
    public static UserRepresentation convert(AddUserRequestDto dto){
        if (dto == null)
            return null;
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(dto.getUsername());
        userRepresentation.setAttributes(dto.getAttributes());
        userRepresentation.setFirstName(dto.getFirstname());
        userRepresentation.setLastName(dto.getSurname());
        userRepresentation.setEmail(dto.getEmail());
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(dto.getPassword());
        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName("ROLE_USER");
//        userRepresentation.setR
        return userRepresentation;
    }

    private static CredentialRepresentation  createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }
}

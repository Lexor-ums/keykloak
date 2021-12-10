package com.example.keycloak.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.keycloak.json.StringListMapDeserializer;

import java.util.List;
import java.util.Map;

@Data
public class AddUserRequestDto {
    private String username = "userID";
    private String firstname = "lex";
    private String surname = "Su";
    private String email = "wfwef";
    private String password = "qwerty";
    @JsonDeserialize(using = StringListMapDeserializer.class)
    protected Map<String, List<String>> attributes;
}

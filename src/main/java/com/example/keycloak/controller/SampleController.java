package com.example.keycloak.controller;

import com.example.keycloak.dto.request.AddUserRequestDto;
import com.example.keycloak.dto.request.LoginRequestDto;
import com.example.keycloak.dto.request.LoginResponceDto;
import com.example.keycloak.service.LoginService;
import com.example.keycloak.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SampleController {
    private final UsersService usersService;
    private final KeycloakRestTemplate keycloakRestTemplate;

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Authentication",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(array =
                    @ArraySchema(schema = @Schema(implementation = String.class)))),
                    @ApiResponse(responseCode = "400", description = "Couldn't send message", content = @Content(array =
                    @ArraySchema(schema = @Schema(implementation = String.class))))})
    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo() {
        return ResponseEntity.ok("All ok, user");
    }

    private final LoginService loginService;

    @PostMapping("login")
    public ResponseEntity<LoginResponceDto> login (HttpServletRequest request,
                                                   @RequestBody LoginRequestDto loginRequest) throws Exception {
        ResponseEntity<LoginResponceDto> response = null;
        response = loginService.login(loginRequest);

        return response;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAminInfo() {
        return ResponseEntity.ok("All ok, admin");
    }


    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "logout";
    }

    @GetMapping(path = "/service_data")
    @PreAuthorize("hasRole('ROLE_SERVICE')")
    public ResponseEntity<?> getServiceData(){
        return ResponseEntity.ok(keycloakRestTemplate.postForEntity("http://localhost:8081/api/data/",null, String.class));
    }

    @PostMapping("/me")
    public Object getMe() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return usersService.getUserDOB(authentication.getName());
    }

    @PostMapping("/add_user")
    public ResponseEntity addUser(@RequestBody AddUserRequestDto dto){
        System.out.println("add user");
        return usersService.addUser(dto);
    }
}

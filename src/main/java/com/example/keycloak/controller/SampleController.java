package com.example.keycloak.controller;

import com.example.keycloak.dto.request.AddUserRequestDto;
import com.example.keycloak.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SampleController {
    private final UsersService usersService;

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

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAminInfo() {
        return ResponseEntity.ok("All ok, admin");
    }


    @GetMapping("/logout")
    public void logout() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        usersService.logout(authentication.getName());
    }

    @GetMapping("/me")
    public Object getMe() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return usersService.getUserDOB(authentication.getName());
    }

    @PostMapping("/ads_user")
    public ResponseEntity addUser(@RequestBody AddUserRequestDto dto){
        return usersService.addUser(dto);
    }
}

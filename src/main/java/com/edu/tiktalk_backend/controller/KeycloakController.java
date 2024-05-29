package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.dto.User;
import com.edu.tiktalk_backend.service.impl.KeycloakServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/keycloak")
@SecurityRequirement(name = "Keycloak")
@RequiredArgsConstructor
public class KeycloakController {
    private final KeycloakServiceImpl keycloakService;

    @Operation(summary = "Зарегистрировать пользователя")
    @PostMapping("/register/")
    public UUID registerUser(@RequestBody User user) {
        return keycloakService.register(user);
    }
}

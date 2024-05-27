package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.dto.User;
import com.edu.tiktalk_backend.dto.request.PersonRequest;
import com.edu.tiktalk_backend.enums.RolesEnum;
import com.edu.tiktalk_backend.mapper.PersonMapper;
import com.edu.tiktalk_backend.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("api/keycloak")
@SecurityRequirement(name = "Keycloak")
@RequiredArgsConstructor
public class KeycloakController {
    private final Keycloak keycloak;
    private final PersonService personService;
    private final PersonMapper personMapper;

    @Value("${spring.keycloak.realm}")
    private String realm;

    @Operation(summary = "Зарегистрировать пользователя")
    @PostMapping("/register/")
    public void registerUser(@RequestBody User user) {
        List<String> defaultRoleNames = keycloak.realm(realm).roles().list().stream()
                .map(RoleRepresentation::getName)
                .toList();

        boolean find = user.getRoles().stream().allMatch(r -> defaultRoleNames.contains(r.getRole()));
        if (!find) {
            throw new RuntimeException("Role doesn't exist");
        }
        List<String> roles = user.getRoles().stream().map(RolesEnum::getRole).toList();
        UserRepresentation userRep = mapPersonRequest(user, roles);
        Response response = keycloak.realm(realm).users().create(userRep);
        if (response.getStatus() != 201) {
            String error = response.readEntity(String.class);
            throw new RuntimeException("Failed to create user: " + error);
        }

        String location = response.getHeaderString("Location");
        String userId = location.substring(location.lastIndexOf('/') + 1);
        if (location.isEmpty()) {
            throw new RuntimeException("Failed to get user ID from response");
        }
        //personService.save(UUID.fromString(userId), personMapper.mapUser(user));
    }

    private UserRepresentation mapPersonRequest(User user, List<String> roles) {
        UserRepresentation userRep = new UserRepresentation();
        userRep.setUsername(user.getUsername());
        userRep.setEmail(user.getEmail());
        userRep.setFirstName(user.getFirstName());
        userRep.setEnabled(true);
        userRep.setCredentials(Collections.singletonList(createPasswordCredentials(user.getPassword())));
        userRep.setRealmRoles(roles);
        return userRep;
    }

    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }
}

package com.edu.tiktalk_backend.service.impl;

import com.edu.tiktalk_backend.dto.User;
import com.edu.tiktalk_backend.mapper.PersonMapper;
import com.edu.tiktalk_backend.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KeycloakServiceImpl {
    private final Keycloak keycloak;
    private final PersonService personService;
    private final PersonMapper personMapper;

    @Value("${spring.keycloak.realm}")
    private String realm;

    @Transactional
    public UUID register(User user) {
        List<RoleRepresentation> defaultRoleNames = keycloak.realm(realm).roles().list().stream()
                .toList();

        Optional<RoleRepresentation> find = defaultRoleNames.stream().filter(r -> Objects.equals(r.getName(), user.getRole().getName())).findFirst();
        if (find.isEmpty()) {
            throw new RuntimeException("Role doesn't exist");
        }
        UserRepresentation userRep = mapPersonRequest(user);
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
        UserResource userResource = keycloak.realm(realm).users().get(userId);

        userResource.roles().realmLevel().add(Collections.singletonList(find.get()));

        return personService.save(UUID.fromString(userId), personMapper.mapUser(user));
    }

    private UserRepresentation mapPersonRequest(User user) {
        UserRepresentation userRep = new UserRepresentation();
        userRep.setUsername(user.getUsername());
        userRep.setEmail(user.getEmail());
        userRep.setFirstName(user.getFirstName());
        userRep.setEnabled(true);
        userRep.setCredentials(Collections.singletonList(createPasswordCredentials(user.getPassword())));
        return userRep;
    }

    private CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }
}

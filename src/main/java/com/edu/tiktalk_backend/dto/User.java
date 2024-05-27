package com.edu.tiktalk_backend.dto;

import com.edu.tiktalk_backend.enums.RolesEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    @Size(min = 2, max = 255)
    private String firstName;

    @NotNull
    private String email;

    @NotNull
    List<RolesEnum> roles;
}

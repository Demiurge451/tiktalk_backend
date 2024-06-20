package com.edu.tiktalk_backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RolesEnum {
    USER("USER"),
    ADMIN("ADMIN");

    private final String name;
}

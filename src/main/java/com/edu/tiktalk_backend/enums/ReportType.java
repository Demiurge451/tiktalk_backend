package com.edu.tiktalk_backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReportType {
    DELETE("podcast is deleted"),
    REJECT("reports are rejected");

    private final String type;
}

package com.edu.tiktalk_backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum ReportSort {
    ID_ASC(Sort.by(Sort.Direction.ASC, "id")),
    ID_DESC(Sort.by(Sort.Direction.DESC, "id")),
    THEME_ASC(Sort.by(Sort.Direction.ASC, "theme")),
    THEME_DESC(Sort.by(Sort.Direction.DESC, "theme"));

    private final Sort sortValue;
}

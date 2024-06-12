package com.edu.tiktalk_backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum ReportedPodcastSort {
    ID_ASC(Sort.by(Sort.Direction.ASC, "id")),
    ID_DESC(Sort.by(Sort.Direction.DESC, "id")),
    NAME_ASC(Sort.by(Sort.Direction.ASC, "name")),
    NAME_DESC(Sort.by(Sort.Direction.DESC, "name")),
    CREATION_DATE_ASC(Sort.by(Sort.Direction.ASC, "creation_date")),
    CREATION_DATE_DESC(Sort.by(Sort.Direction.DESC, "creation_date"));

    private final Sort sortValue;
}

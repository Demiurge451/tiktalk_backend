package com.edu.tiktalk_backend.sort_enum;

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
    CREATION_DATE_ASC(Sort.by(Sort.Direction.ASC, "creationDate")),
    CREATION_DATE_DESC(Sort.by(Sort.Direction.DESC, "creationDate"));

    private final Sort sortValue;
}

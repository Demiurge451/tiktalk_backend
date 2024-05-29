package com.edu.tiktalk_backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum PodcastSort {
    ID_ASC(Sort.by(Sort.Direction.ASC, "id")),
    ID_DESC(Sort.by(Sort.Direction.DESC, "id")),
    NAME_ASC(Sort.by(Sort.Direction.ASC, "name")),
    NAME_DESC(Sort.by(Sort.Direction.DESC, "name")),
    LIKE_ASC(Sort.by(Sort.Direction.ASC, "likes")),
    LIKE_DESK(Sort.by(Sort.Direction.DESC, "likes")),
    REPORTS_COUNT_ASC(Sort.by(Sort.Direction.ASC, "reportsCount")),
    REPORTS_COUNT_DESC(Sort.by(Sort.Direction.DESC, "reportsCount"));

    private final Sort sortValue;
}

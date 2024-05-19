package com.edu.tiktalk_backend.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public enum BucketEnum {
    IMAGE_BUCKET("images"),
    PODCAST_BUCKET("podcasts");

    private final String value;
}

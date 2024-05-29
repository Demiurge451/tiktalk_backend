package com.edu.tiktalk_backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketEnum {
    IMAGE_BUCKET("images"),
    PODCAST_BUCKET("podcasts");

    private final String value;
}

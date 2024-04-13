package com.edu.tiktalk_backend.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PodcastRequest {
    private String name;
    private String description;
    private UUID albumId;
    private UUID personId;
}

package com.edu.tiktalk_backend.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportedPodcastResponse {
    private UUID id;

    private String name;

    private String description;

    private UUID personId;

    private UUID albumId;

    private String imageUrl;

    private String audioUrl;

    private String verdict;

    private LocalDateTime creationDate;

    private String reportType;
}

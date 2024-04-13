package com.edu.tiktalk_backend.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {
    private UUID id;
    private String theme;
    private String description;
    private UUID personId;
    private UUID podcastId;
}

package com.edu.tiktalk_backend.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportRequest {
    private String theme;
    private String description;
    private UUID personId;
    private UUID podcastId;
}

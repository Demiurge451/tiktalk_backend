package com.edu.tiktalk_backend.dto.response;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    private UUID reporterId;

    private UUID podcastId;
}

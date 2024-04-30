package com.edu.tiktalk_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportRequest {
    @NotBlank
    @Size(min = 1, max = 55)
    private String theme;

    @Size(min = 1, max = 255)
    private String description;

    @NotNull
    private UUID personId;

    @NotNull
    private UUID podcastId;
}

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
public class ReportedPodcastRequest {
    private String name;

    private String description;

    private UUID albumId;

    private UUID personId;

    private String verdict;

    private String imageUrl;

    private String audioUrl;
}

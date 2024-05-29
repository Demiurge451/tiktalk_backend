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
public class PodcastRequest {
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @Size(min = 1, max = 255)
    private String description;

    @NotNull
    private UUID albumId;
}

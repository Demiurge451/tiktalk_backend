package com.edu.tiktalk_backend.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PodcastResponse {
    @NotNull
    private UUID id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @Size(max = 255)
    private String description;

    @NotNull
    private UUID personId;

    @NotNull
    private UUID albumId;

    private List<UUID> reports;
    private List<UUID> likedPersons;
}

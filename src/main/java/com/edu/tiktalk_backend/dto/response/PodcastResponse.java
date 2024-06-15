package com.edu.tiktalk_backend.dto.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PodcastResponse {
    private UUID id;

    private String name;

    private String description;

    private UUID personId;

    private UUID albumId;

    private List<UUID> reports;

    private List<UUID> likedPersons;

    public int likes;

    public int reportsCount;

    private String imageUrl;

    private String audioUrl;

    private LocalDateTime creationDate;
}

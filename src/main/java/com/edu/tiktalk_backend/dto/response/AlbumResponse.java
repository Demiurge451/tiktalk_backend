package com.edu.tiktalk_backend.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlbumResponse {
    private UUID id;

    @NotBlank
    @Size(max = 50)
    private String title;

    @Size(max = 255)
    private String description;
    private List<UUID> podcasts;
}

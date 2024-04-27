package com.edu.tiktalk_backend.dto.response;

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
    private String title;
    private String description;
    private List<UUID> podcasts;
}

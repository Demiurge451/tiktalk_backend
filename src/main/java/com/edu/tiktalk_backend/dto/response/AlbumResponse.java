package com.edu.tiktalk_backend.dto.response;

import com.edu.tiktalk_backend.model.Podcast;
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
    private String name;
    private String description;
    private List<PodcastResponse> podcasts;
}

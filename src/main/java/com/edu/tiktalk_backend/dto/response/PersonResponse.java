package com.edu.tiktalk_backend.dto.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponse {
    private UUID id;
    private List<PodcastResponse> podcasts;
    private List<UUID> subscribers;
    private List<PodcastResponse> likedPodcasts;
    private List<ReportResponse> reports;
}

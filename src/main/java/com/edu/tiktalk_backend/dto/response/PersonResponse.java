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
    private List<UUID> podcasts;
    private List<UUID> subscriptions;
    private List<UUID> likedPodcasts;
    private List<UUID> reports;
}

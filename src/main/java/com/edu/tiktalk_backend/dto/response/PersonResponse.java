package com.edu.tiktalk_backend.dto.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponse {
    @NotNull
    private UUID id;

    private List<UUID> podcasts;
    private List<UUID> subscriptions;
    private List<UUID> likedPodcasts;
    private List<UUID> reports;
}

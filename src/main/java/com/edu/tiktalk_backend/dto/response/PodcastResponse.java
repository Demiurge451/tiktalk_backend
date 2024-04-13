package com.edu.tiktalk_backend.dto.response;

import com.edu.tiktalk_backend.model.Person;
import lombok.*;

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
    private List<ReportResponse> reports;
    private List<UUID> likedPersons;
}

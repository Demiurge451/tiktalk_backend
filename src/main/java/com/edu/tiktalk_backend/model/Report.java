package com.edu.tiktalk_backend.model;

import com.edu.tiktalk_backend.model.id_container.IdContainer;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report extends IdContainer<UUID> {
    private String theme;

    private String description;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "podcast_id")
    private Podcast podcast;
}

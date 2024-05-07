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

    @ManyToOne(targetEntity = Person.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", insertable = false, updatable = false)
    private Person person;

    @Column(name = "person_id")
    private UUID personId;

    @ManyToOne(targetEntity = Podcast.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "podcast_id", insertable = false, updatable = false)
    private Podcast podcast;

    @Column(name = "podcast_id")
    private UUID podcastId;
}

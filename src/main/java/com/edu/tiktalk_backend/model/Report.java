package com.edu.tiktalk_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report implements HasId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected UUID id;

    @Column(name = "theme")
    private String theme;

    @Column(name = "description")
    private String description;

    @ManyToOne(targetEntity = Person.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "reporter_id", insertable = false, updatable = false)
    private Person reporter;

    @Column(name = "reporter_id")
    private UUID reporterId;

    @ManyToOne(targetEntity = Podcast.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "podcast_id", insertable = false, updatable = false)
    private Podcast podcast;

    @Column(name = "podcast_id")
    private UUID podcastId;
}

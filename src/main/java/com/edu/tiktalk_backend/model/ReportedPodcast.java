package com.edu.tiktalk_backend.model;

import com.edu.tiktalk_backend.model.id_container.IdContainer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportedPodcast extends IdContainer<UUID> {
    @Column(name = "creationDate", nullable = false)
    private final LocalDateTime creationDate = LocalDateTime.now();

    private String name;

    private String description;

    @ManyToOne(targetEntity = Person.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", insertable = false, updatable = false)
    private Person person;

    @Column(name = "person_id")
    private UUID personId;

    @ManyToOne(targetEntity = Album.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "album_id", insertable = false, updatable = false)
    private Album album;

    @Column(name = "album_id")
    private UUID albumId;

    private String audioUrl;

    private String imageUrl;

    private String verdict;
}

package com.edu.tiktalk_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Podcast implements HasId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected UUID id;

    @Version
    private long version;

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

    @OneToMany(mappedBy = "podcast", cascade = CascadeType.ALL)
    private List<Report> reports;

    @ManyToMany(mappedBy = "likedPodcasts", cascade = CascadeType.ALL)
    private List<Person> likedPersons;

    private int likes;

    private int reportsCount;

    private String audioUrl;

    private String imageUrl;

    @Column(name = "creation_date", insertable = false, updatable = false)
    private LocalDateTime creationDate;
}

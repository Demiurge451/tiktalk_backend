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
    @Column(name = "id")
    protected UUID id;

    @Version
    @Column(name = "version")
    private long version;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
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

    @Column(name = "likes")
    private int likes;

    @Column(name = "reports_count")
    private int reportsCount;

    @Column(name = "audio_url")
    private String audioUrl;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "creation_date", insertable = false, updatable = false)
    private LocalDateTime creationDate;
}

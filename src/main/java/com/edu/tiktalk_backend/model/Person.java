package com.edu.tiktalk_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person implements HasId {
    @Id
    private UUID id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "subscriptions",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Person> subscriptions;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Podcast> podcasts;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Album> albums;

    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL)
    private List<Report> reports;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "liked_podcasts",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "podcast_id")
    )
    private List<Podcast> likedPodcasts;

    private String imageUrl;
}

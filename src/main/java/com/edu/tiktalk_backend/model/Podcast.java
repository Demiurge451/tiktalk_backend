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
public class Podcast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @OneToMany(mappedBy = "podcast", cascade = CascadeType.ALL)
    private List<Report> reports;

    @ManyToMany(mappedBy = "likedPodcasts", cascade = CascadeType.ALL)
    private List<Person> likedPersons;
}

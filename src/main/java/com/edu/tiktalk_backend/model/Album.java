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
public class Album implements HasId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected UUID id;

    private String title;

    private String description;

    @ManyToOne(targetEntity = Person.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", insertable = false, updatable = false)
    private Person person;

    @Column(name = "person_id")
    private UUID personId;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<Podcast> podcasts;
}

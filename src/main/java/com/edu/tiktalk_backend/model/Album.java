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
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<Podcast> podcasts;
}

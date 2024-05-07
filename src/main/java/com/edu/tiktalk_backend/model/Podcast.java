package com.edu.tiktalk_backend.model;

import com.edu.tiktalk_backend.model.id_container.IdContainer;
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
public class Podcast extends IdContainer<UUID> {
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

    private int reports_count;
}

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
public class Person extends IdContainer<UUID> {
    @ManyToMany
    @JoinTable(
            name = "subscribers",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id")
    )
    private List<Person> subscribers;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Podcast> podcasts;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Report> reports;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "liked_podcasts",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "podcast_id")
    )
    private List<Podcast> likedPodcasts;
}

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
public class Album extends IdContainer<UUID> {
    private String name;

    private String description;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<Podcast> podcasts;
}

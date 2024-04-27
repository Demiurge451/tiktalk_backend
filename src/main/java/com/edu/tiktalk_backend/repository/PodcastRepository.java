package com.edu.tiktalk_backend.repository;

import com.edu.tiktalk_backend.model.Podcast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PodcastRepository extends JpaRepository<Podcast, UUID> {
    List<Podcast> findAllByNameLikeIgnoreCase(String name);
}

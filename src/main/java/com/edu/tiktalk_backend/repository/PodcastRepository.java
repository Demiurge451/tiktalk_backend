package com.edu.tiktalk_backend.repository;

import com.edu.tiktalk_backend.model.Podcast;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PodcastRepository extends JpaRepository<Podcast, UUID> {

    @Query(value = "select p.*" +
            " from podcast as p" +
            " join followers as f on p.person_id = f.author_id" +
            " where follower_id = :person_id",
            nativeQuery = true)
    Page<Podcast> getSubscribedPodcasts(Pageable pageable, @Param("person_id") UUID personId);

    Page<Podcast> findAllByNameLikeIgnoreCase(Pageable pageRequest, String name);
}

package com.edu.tiktalk_backend.repository;

import com.edu.tiktalk_backend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
    @Modifying
    @Query(value = "insert into subscriptions values(:person_id, :author_id)", nativeQuery = true)
    void follow(@Param("person_id") UUID personId, @Param("author_id") UUID authorId);

    @Modifying
    @Query(value = "insert into liked_podcasts values(:person_id, :podcast_id)", nativeQuery = true)
    void like(@Param("person_id") UUID personId, @Param("podcast_id") UUID podcastId);
}

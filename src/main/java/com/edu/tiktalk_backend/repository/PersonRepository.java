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
    @Query(value = "insert into followers values(:follower_id, :author_id)", nativeQuery = true)
    void follow(@Param("follower_id") UUID follower_id, @Param("author_id") UUID authorId);

    @Modifying
    @Query(value = "insert into liked_podcasts values(:person_id, :podcast_id)", nativeQuery = true)
    void like(@Param("person_id") UUID personId, @Param("podcast_id") UUID podcastId);

    @Modifying
    @Query(value = "delete from liked_podcasts where person_id = :person_id and podcast_id = :podcast_id", nativeQuery = true)
    void unlike(@Param("person_id") UUID personId, @Param("podcast_id") UUID podcastId);

    @Modifying
    @Query(value = "delete from followers where follower_id = :follower_id and author_id = :author_id", nativeQuery = true)
    void unfollow(@Param("follower_id") UUID followerId, @Param("author_id") UUID authorId);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM liked_podcasts WHERE person_id = :person_id AND podcast_id = :podcast_id)", nativeQuery = true)
    boolean isPodcastLiked(@Param("person_id") UUID personId, @Param("podcast_id") UUID podcastId);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM followers WHERE follower_id = :follower_id AND author_id = :author_id)", nativeQuery = true)
    boolean isPersonFollowed(@Param("follower_id") UUID followerId, @Param("author_id") UUID authorId);
}

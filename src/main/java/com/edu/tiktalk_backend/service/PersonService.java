package com.edu.tiktalk_backend.service;

import com.edu.tiktalk_backend.model.Person;
import com.edu.tiktalk_backend.model.Podcast;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface PersonService extends CrudService<Person, UUID> {
    void follow(UUID loginId, UUID authorId);

    void like(UUID loginId, UUID podcastId);

    void unlike(UUID loginId, UUID podcastId);

    void unfollow(UUID loginId, UUID authorId);

    void upload(UUID loginId, MultipartFile file);

    boolean isPodcastLiked(UUID personId, UUID podcastId);

    boolean isPersonFollowed(UUID followerId, UUID authorId);
}

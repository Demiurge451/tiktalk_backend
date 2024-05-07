package com.edu.tiktalk_backend.service;

import com.edu.tiktalk_backend.model.Person;
import com.edu.tiktalk_backend.model.Podcast;

import java.util.UUID;

public interface PersonService extends CrudService<Person, UUID> {
    void follow(UUID personId, UUID authorId);
    void like(UUID personId, UUID podcastId);
    void unlike(UUID personId, UUID podcastId);
    void unfollow(UUID followerId, UUID authorId);
    void report(UUID reporterId, UUID podcastId);
}

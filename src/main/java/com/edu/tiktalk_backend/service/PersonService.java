package com.edu.tiktalk_backend.service;

import com.edu.tiktalk_backend.model.Person;
import com.edu.tiktalk_backend.model.Podcast;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface PersonService {
    void follow(UUID personId, UUID authorId);
    void like(UUID personId, UUID podcastId);
    void unlike(UUID personId, UUID podcastId);
    void unfollow(UUID followerId, UUID authorId);
    UUID save(Person person, MultipartFile image);
    List<Person> getAll(PageRequest pageRequest);
    Person getById(UUID id);
    void delete(UUID id);
    Person update(UUID id, Person item);
}

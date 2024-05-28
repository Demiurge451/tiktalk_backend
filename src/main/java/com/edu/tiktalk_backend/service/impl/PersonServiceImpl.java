package com.edu.tiktalk_backend.service.impl;


import com.edu.tiktalk_backend.exception.NotFoundException;
import com.edu.tiktalk_backend.mapper.PersonMapper;
import com.edu.tiktalk_backend.model.Person;
import com.edu.tiktalk_backend.model.Podcast;
import com.edu.tiktalk_backend.repository.PersonRepository;
import com.edu.tiktalk_backend.service.PersonService;
import com.edu.tiktalk_backend.service.PodcastService;
import com.edu.tiktalk_backend.enums.BucketEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    protected final PersonRepository personRepository;
    protected final PersonMapper personMapper;
    private final PodcastService podcastService;
    private final DownloadService downloadService;

    @Override
    public List<Person> getAll(PageRequest pageRequest) {
        return personRepository.findAll(pageRequest).getContent();
    }

    @Override
    public Person getById(UUID id) {
        return personRepository.findById(id).orElseThrow(() -> new NotFoundException("Person not found"));
    }

    @Override
    @Transactional
    public void delete(UUID loginId, UUID id) {
        personRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void upload(UUID loginId, MultipartFile image) {
        Person person = getById(loginId);
        person.setImageUrl(downloadService.upload(image, BucketEnum.IMAGE_BUCKET));
        personRepository.save(person);
    }

    @Override
    @Transactional
    public Person update(UUID loginId, UUID personId, Person item) {
        checkBelong(loginId, personId);
        Person person = getById(personId);
        personMapper.updatePerson(item, person);
        return personRepository.save(person);
    }

    @Override
    @Transactional
    public void follow(UUID loginId, UUID authorId) {
        if (loginId != authorId) {
            personRepository.follow(loginId, authorId);
        }
    }

    @Override
    @Transactional
    public void like(UUID loginId, UUID podcastId) {
        personRepository.like(loginId, podcastId);
        Podcast podcast = podcastService.getById(podcastId);
        podcast.setLikes(podcast.getLikes() + 1);
        podcastService.update(loginId, podcastId, podcast);
    }

    @Override
    @Transactional
    public void unlike(UUID loginId, UUID podcastId) {
        personRepository.unlike(loginId, podcastId);
        Podcast podcast = podcastService.getById(podcastId);
        podcast.setLikes(podcast.getLikes() - 1);
        podcastService.update(loginId, podcastId, podcast);
    }

    @Override
    @Transactional
    public void unfollow(UUID loginId, UUID authorId) {
        personRepository.unfollow(loginId, authorId);
    }

    @Override
    @Transactional
    public UUID save(UUID loginId, Person person) {
        person.setId(loginId);
        Person p = personRepository.save(person);
        return p.getId();
    }

    @Override
    public boolean isPodcastLiked(UUID personId, UUID podcastId) {
        return personRepository.isPodcastLiked(personId, podcastId);
    }

    @Override
    public boolean isPersonFollowed(UUID followerId, UUID authorId) {
        return personRepository.isPersonFollowed(followerId, authorId);
    }

    @Override
    public void checkBelong(UUID loginId, UUID personId) {
        if (!loginId.equals(getById(personId).getId())) {
            throw new RuntimeException("login person isn't person for update");
        }
    }
}

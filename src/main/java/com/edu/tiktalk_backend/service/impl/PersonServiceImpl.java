package com.edu.tiktalk_backend.service.impl;


import com.edu.tiktalk_backend.exception.NotFoundException;
import com.edu.tiktalk_backend.mapper.PersonMapper;
import com.edu.tiktalk_backend.model.Person;
import com.edu.tiktalk_backend.model.Podcast;
import com.edu.tiktalk_backend.repository.PersonRepository;
import com.edu.tiktalk_backend.service.CrudService;
import com.edu.tiktalk_backend.service.PersonService;
import com.edu.tiktalk_backend.service.PodcastService;
import com.edu.tiktalk_backend.service.enums.BucketEnum;
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
    public void delete(UUID id) {
        personRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UUID save(Person person, MultipartFile image) {
        person.setImageUrl(downloadService.upload(image, BucketEnum.IMAGE_BUCKET));
        return personRepository.save(person).getId();
    }

    @Override
    @Transactional
    public Person update(UUID id, Person item) {
        Person person = getById(id);
        personMapper.updatePerson(item, person);
        return personRepository.save(person);
    }

    @Override
    @Transactional
    public void follow(UUID personId, UUID authorId) {
        personRepository.follow(personId, authorId);
    }

    @Override
    @Transactional
    public void like(UUID personId, UUID podcastId) {
        personRepository.like(personId, podcastId);
        Podcast podcast = podcastService.getById(podcastId);
        podcast.setLikes(podcast.getLikes() + 1);
        podcastService.update(podcastId, podcast);
    }

    @Override
    @Transactional
    public void unlike(UUID personId, UUID podcastId) {
        personRepository.unlike(personId, podcastId);
        Podcast podcast = podcastService.getById(podcastId);
        podcast.setLikes(podcast.getLikes() - 1);
        podcastService.update(podcastId, podcast);
    }

    @Override
    @Transactional
    public void unfollow(UUID followerId, UUID authorId) {
        personRepository.unfollow(followerId, authorId);
    }
}

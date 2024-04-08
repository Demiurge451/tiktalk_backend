package com.edu.tiktalk_backend.service.impl;

import com.edu.tiktalk_backend.exception.NotFoundException;
import com.edu.tiktalk_backend.model.Podcast;
import com.edu.tiktalk_backend.repository.PodcastRepository;
import com.edu.tiktalk_backend.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PodcastServiceImpl implements CrudService<Podcast, UUID> {
    private final PodcastRepository podcastRepository;
    @Override
    public List<Podcast> getListOfItems(PageRequest pageRequest) {
        return podcastRepository.findAll(pageRequest).getContent();
    }

    @Override
    public Podcast getById(UUID id) {
        return podcastRepository.findById(id).orElseThrow(() -> new NotFoundException("Podcast not found"));
    }

    @Override
    public void delete(UUID id) {
        podcastRepository.delete(getById(id));
    }

    @Override
    public void save(Podcast podcast) {
        podcastRepository.save(podcast);
    }

    @Override
    public Podcast update(UUID uuid, Podcast item) {
        return null;
    }
}

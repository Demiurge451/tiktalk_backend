package com.edu.tiktalk_backend.service.impl;

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
        return null;
    }

    @Override
    public Podcast getById(UUID uuid) {
        return null;
    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public void save(Podcast item) {

    }

    @Override
    public Podcast update(UUID uuid, Podcast item) {
        return null;
    }
}

package com.edu.tiktalk_backend.service.impl;

import com.edu.tiktalk_backend.exception.NotFoundException;
import com.edu.tiktalk_backend.mapper.PodcastMapper;
import com.edu.tiktalk_backend.model.Podcast;
import com.edu.tiktalk_backend.repository.PodcastRepository;
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
public class PodcastServiceImpl {
    private final PodcastRepository podcastRepository;
    private final PodcastMapper podcastMapper;
    private final DownloadService downloadService;

    public List<Podcast> getAll(PageRequest pageRequest) {
        return podcastRepository.findAll(pageRequest).getContent();
    }

    public Podcast getById(UUID id) {
        return podcastRepository.findById(id).orElseThrow(() -> new NotFoundException("Podcast not found"));
    }

    @Transactional
    public void delete(UUID id) {
        podcastRepository.delete(getById(id));
    }

    @Transactional
    public Podcast save(Podcast podcast, MultipartFile audio, MultipartFile image) {
        podcast.setAudioUrl(downloadService.upload(audio, BucketEnum.PODCAST_BUCKET));
        podcast.setImageUrl(downloadService.upload(image, BucketEnum.IMAGE_BUCKET));
        return podcastRepository.save(podcast);
    }

    @Transactional
    public Podcast update(UUID id, Podcast item) {
        Podcast podcast = getById(id);
        podcastMapper.updatePodcast(item, podcast);
        return podcastRepository.save(podcast);
    }

    public List<Podcast> findByName(String name) {
        return podcastRepository.findAllByNameLikeIgnoreCase("%" + name + "%");
    }
}

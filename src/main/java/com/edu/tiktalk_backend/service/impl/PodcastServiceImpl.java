package com.edu.tiktalk_backend.service.impl;

import com.edu.tiktalk_backend.exception.NotFoundException;
import com.edu.tiktalk_backend.mapper.PodcastMapper;
import com.edu.tiktalk_backend.mapper.ReportedPodcastMapper;
import com.edu.tiktalk_backend.model.Podcast;
import com.edu.tiktalk_backend.model.Report;
import com.edu.tiktalk_backend.model.ReportedPodcast;
import com.edu.tiktalk_backend.repository.PodcastRepository;
import com.edu.tiktalk_backend.service.PodcastService;
import com.edu.tiktalk_backend.service.ReportService;
import com.edu.tiktalk_backend.service.ReportedPodcastService;
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
public class PodcastServiceImpl implements PodcastService {
    private final PodcastRepository podcastRepository;
    private final PodcastMapper podcastMapper;
    private final DownloadService downloadService;
    private final ReportedPodcastService reportedPodcastService;
    private final ReportedPodcastMapper reportedPodcastMapper;
    private final ReportService reportService;

    @Override
    public List<Podcast> getAll(PageRequest pageRequest) {
        return podcastRepository.findAll(pageRequest).getContent();
    }

    @Override
    public List<Podcast> getSubscribedPodcasts(PageRequest pageRequest, UUID loginId) {
        return podcastRepository.getSubscribedPodcasts(pageRequest, loginId).getContent();
    }

    @Override
    public Podcast getById(UUID id) {
        return podcastRepository.findById(id).orElseThrow(() -> new NotFoundException("Podcast not found"));
    }

    @Override
    @Transactional
    public void delete(UUID loginId, UUID podcastId) {
        checkBelong(loginId, podcastId);
        podcastRepository.deleteById(podcastId);
    }

    @Override
    @Transactional
    public UUID save(UUID personId, Podcast podcast) {
        podcast.setPersonId(personId);
        Podcast p = podcastRepository.save(podcast);
        return p.getId();
    }

    @Override
    @Transactional
    public Podcast update(UUID personId, UUID podcastId, Podcast item) {
        checkBelong(personId, podcastId);
        item.setPersonId(personId);
        Podcast podcast = getById(podcastId);
        podcastMapper.updatePodcast(item, podcast);
        return podcastRepository.save(podcast);
    }

    @Override
    public List<Podcast> findByName(PageRequest pageRequest, String name) {
        return podcastRepository.findAllByNameLikeIgnoreCase(pageRequest,"%" + name + "%").getContent();
    }

    @Override
    @Transactional
    public UUID banPodcast(UUID id, String verdict) {
        Podcast podcast = getById(id);
        podcastRepository.deleteById(id);
        ReportedPodcast banned = reportedPodcastMapper.mapRequestToItem(podcastMapper.mapPodcastToReported(podcast));
        banned.setVerdict(verdict);
        return reportedPodcastService.save(banned);
    }

    @Override
    @Transactional
    public UUID rejectReports(UUID id, String verdict) {
        reportService.deleteAllByPodcast(id);
        Podcast podcast = getById(id);
        podcast.setReportsCount(0);
        podcastRepository.save(podcast);
        ReportedPodcast banned = reportedPodcastMapper.mapRequestToItem(podcastMapper.mapPodcastToReported(podcast));
        banned.setVerdict(verdict);
        return reportedPodcastService.save(banned);
    }

    @Override
    @Transactional
    public void upload(UUID personId, UUID podcastId, MultipartFile audio, MultipartFile image) {
        checkBelong(personId, podcastId);
        Podcast podcast = getById(podcastId);
        podcast.setAudioUrl(downloadService.upload(audio, BucketEnum.PODCAST_BUCKET));
        podcast.setImageUrl(downloadService.upload(image, BucketEnum.IMAGE_BUCKET));
        podcastRepository.save(podcast);
    }

    @Override
    @Transactional
    public UUID reportPodcast(UUID personId, Report report) {
        checkBelong(personId, report.getPodcastId());
        Podcast podcast = getById(report.getPodcastId());
        podcast.setReportsCount(podcast.getReportsCount() + 1);
        podcastRepository.save(podcast);
        return reportService.save(personId, report);
    }

    @Override
    public void checkBelong(UUID personId, UUID podcastId) {
        if (!getById(podcastId).getPersonId().equals(personId)) {
            throw new RuntimeException("podcast doesn't belong to person");
        }
    }
}

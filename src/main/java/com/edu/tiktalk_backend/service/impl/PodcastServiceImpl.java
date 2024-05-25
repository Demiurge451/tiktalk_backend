package com.edu.tiktalk_backend.service.impl;

import com.edu.tiktalk_backend.dto.request.ReportedPodcastRequest;
import com.edu.tiktalk_backend.exception.NotFoundException;
import com.edu.tiktalk_backend.mapper.PodcastMapper;
import com.edu.tiktalk_backend.mapper.ReportedPodcastMapper;
import com.edu.tiktalk_backend.model.Podcast;
import com.edu.tiktalk_backend.model.Report;
import com.edu.tiktalk_backend.model.ReportedPodcast;
import com.edu.tiktalk_backend.repository.PodcastRepository;
import com.edu.tiktalk_backend.repository.ReportedPodcastRepository;
import com.edu.tiktalk_backend.service.PodcastService;
import com.edu.tiktalk_backend.service.ReportService;
import com.edu.tiktalk_backend.service.ReportedPodcastService;
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
    public Podcast getById(UUID id) {
        return podcastRepository.findById(id).orElseThrow(() -> new NotFoundException("Podcast not found"));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        podcastRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UUID save(Podcast podcast, MultipartFile audio, MultipartFile image) {
        podcast.setAudioUrl(downloadService.upload(audio, BucketEnum.PODCAST_BUCKET));
        podcast.setImageUrl(downloadService.upload(image, BucketEnum.IMAGE_BUCKET));
        return podcastRepository.save(podcast).getId();
    }

    @Override
    @Transactional
    public Podcast update(UUID id, Podcast item) {
        Podcast podcast = getById(id);
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
    public void rejectReports(UUID id, String verdict) {
        reportService.deleteAllByPodcast(id);
        Podcast podcast = getById(id);
        podcast.setReportsCount(0);
        update(id, podcast);
    }

    @Override
    @Transactional
    public UUID reportPodcast(Report report) {
        Podcast podcast = getById(report.getPodcastId());
        podcast.setReportsCount(podcast.getReportsCount() + 1);
        update(podcast.getId(), podcast);
        return reportService.save(report);
    }
}

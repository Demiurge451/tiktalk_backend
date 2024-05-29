package com.edu.tiktalk_backend.service.impl;

import com.edu.tiktalk_backend.exception.NotFoundException;
import com.edu.tiktalk_backend.model.ReportedPodcast;
import com.edu.tiktalk_backend.repository.ReportedPodcastRepository;
import com.edu.tiktalk_backend.service.ReportedPodcastService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportedPodcastServiceImpl implements ReportedPodcastService {
    private final ReportedPodcastRepository reportedPodcastRepository;

    @Override
    public List<ReportedPodcast> getAll(PageRequest pageRequest) {
        return reportedPodcastRepository.findAll(pageRequest).getContent();
    }

    @Override
    public ReportedPodcast getById(UUID id) {
        return reportedPodcastRepository.findById(id).orElseThrow(() -> new NotFoundException("Podcast not found"));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        reportedPodcastRepository.deleteById(id);
    }

    @Override
    public UUID save(ReportedPodcast reportedPodcast) {
        ReportedPodcast podcast = reportedPodcastRepository.save(reportedPodcast);
        return podcast.getId();
    }
}

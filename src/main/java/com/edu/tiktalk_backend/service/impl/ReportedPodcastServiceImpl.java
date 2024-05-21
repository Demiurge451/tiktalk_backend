package com.edu.tiktalk_backend.service.impl;

import com.edu.tiktalk_backend.exception.NotFoundException;
import com.edu.tiktalk_backend.mapper.PodcastMapper;
import com.edu.tiktalk_backend.model.Podcast;
import com.edu.tiktalk_backend.model.ReportedPodcast;
import com.edu.tiktalk_backend.repository.PodcastRepository;
import com.edu.tiktalk_backend.repository.ReportedPodcastRepository;
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
public class ReportedPodcastServiceImpl {
    private final ReportedPodcastRepository reportedPodcastRepository;

    public List<ReportedPodcast> getAll(PageRequest pageRequest) {
        return reportedPodcastRepository.findAll(pageRequest).getContent();
    }

    public ReportedPodcast getById(UUID id) {
        return reportedPodcastRepository.findById(id).orElseThrow(() -> new NotFoundException("Podcast not found"));
    }

    @Transactional
    public void delete(UUID id) {
        reportedPodcastRepository.delete(getById(id));
    }
}

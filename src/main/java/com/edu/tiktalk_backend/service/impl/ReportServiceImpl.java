package com.edu.tiktalk_backend.service.impl;

import com.edu.tiktalk_backend.exception.NotFoundException;
import com.edu.tiktalk_backend.mapper.ReportMapper;
import com.edu.tiktalk_backend.model.Podcast;
import com.edu.tiktalk_backend.model.Report;
import com.edu.tiktalk_backend.repository.PodcastRepository;
import com.edu.tiktalk_backend.repository.ReportRepository;
import com.edu.tiktalk_backend.service.CrudService;
import com.edu.tiktalk_backend.service.PodcastService;
import com.edu.tiktalk_backend.service.ReportService;
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
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;

    @Override
    public List<Report> getAll(PageRequest pageRequest) {
        return reportRepository.findAll(pageRequest).getContent();
    }

    @Override
    public Report getById(UUID id) {
        return reportRepository.findById(id).orElseThrow(() -> new NotFoundException("Person not found"));
    }

    @Override
    public List<Report> getAllByPodcast(PageRequest pageRequest, UUID id) {
        return reportRepository.findAllByReporterId(pageRequest, id).getContent();
    }

    @Override
    @Transactional
    public void deleteAllByPodcast(UUID podcastId) {
        reportRepository.deleteALlByPodcastId(podcastId);
    }

    @Override
    @Transactional
    public UUID save(Report report) {
        return reportRepository.save(report).getId();
    }
}

package com.edu.tiktalk_backend.service;

import com.edu.tiktalk_backend.model.Podcast;
import com.edu.tiktalk_backend.model.ReportedPodcast;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ReportedPodcastService {
    List<ReportedPodcast> getAll(PageRequest pageRequest);
    ReportedPodcast getById(UUID id);
    void delete(UUID id);
}

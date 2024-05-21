package com.edu.tiktalk_backend.service;

import com.edu.tiktalk_backend.model.Podcast;
import com.edu.tiktalk_backend.model.Report;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface PodcastService {
    List<Podcast> getAll(PageRequest pageRequest);
    Podcast getById(UUID id);
    UUID save(Podcast podcast, MultipartFile audio, MultipartFile image);
    void delete(UUID id);
    Podcast update(UUID id, Podcast podcast);
    List<Podcast> findByName(PageRequest pageRequest, String name);
    UUID banPodcast(UUID id, String verdict);
    UUID rejectReports(UUID id, String verdict);
}

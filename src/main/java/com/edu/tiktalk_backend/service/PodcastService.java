package com.edu.tiktalk_backend.service;

import com.edu.tiktalk_backend.dto.response.PodcastResponse;
import com.edu.tiktalk_backend.model.Podcast;
import com.edu.tiktalk_backend.model.Report;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface PodcastService extends CrudService<Podcast, UUID> {
    List<Podcast> getSubscribedPodcasts(PageRequest pageRequest, UUID loginId);

    List<Podcast> findByName(PageRequest pageRequest, String name);

    UUID banPodcast(UUID id, String verdict);

    UUID rejectReports(UUID id, String verdict);

    UUID reportPodcast(UUID reporterId, Report report);

    void upload(UUID personId, UUID podcastId, MultipartFile audio, MultipartFile image);
}

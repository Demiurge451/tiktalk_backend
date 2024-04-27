package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.dto.request.PodcastRequest;
import com.edu.tiktalk_backend.dto.response.PodcastResponse;
import com.edu.tiktalk_backend.mapper.PodcastMapper;
import com.edu.tiktalk_backend.model.Podcast;
import com.edu.tiktalk_backend.service.CrudService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/podcast")
public class PodcastController {
    private final CrudService<Podcast, UUID> podcastService;
    private final PodcastMapper podcastMapper;

    public PodcastController(CrudService<Podcast, UUID> podcastService, PodcastMapper podcastMapper) {
        this.podcastService = podcastService;
        this.podcastMapper = podcastMapper;
    }

    @GetMapping("/")
    public List<PodcastResponse> getPodcasts(@RequestParam(required = false, defaultValue = "0") int page,
                                     @RequestParam(required = false, defaultValue = "10") int size,
                                     @RequestParam(required = false, defaultValue = "id") String sortParam) {
        return podcastService.getAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortParam)))
                .stream().map(podcastMapper::mapItemToResponse).toList();
    }

    @GetMapping("/{id}")
    public PodcastResponse getPodcast(@PathVariable UUID id) {
        return podcastMapper.mapItemToResponse(podcastService.getById(id));
    }

    @PostMapping("/")
    public void createPodcast(@RequestBody PodcastRequest podcastRequest) {
        podcastService.save(podcastMapper.mapRequestToItem(podcastRequest));
    }

    @DeleteMapping("/{id}")
    public void deletePodcast(@PathVariable UUID id) {
        podcastService.delete(id);
    }

    @PutMapping("/{id}")
    public PodcastResponse updatePodcast(@PathVariable UUID id, @RequestBody PodcastRequest podcastRequest) {
        return podcastMapper.mapItemToResponse(podcastService.update(id, podcastMapper.mapRequestToItem(podcastRequest)));
    }
}

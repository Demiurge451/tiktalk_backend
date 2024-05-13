package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.dto.request.PodcastRequest;
import com.edu.tiktalk_backend.dto.response.PodcastResponse;
import com.edu.tiktalk_backend.mapper.PodcastMapper;
import com.edu.tiktalk_backend.service.impl.MinioService;
import com.edu.tiktalk_backend.sort_enum.PodcastSort;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import com.edu.tiktalk_backend.service.impl.PodcastServiceImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/podcast")
public class PodcastController {
    private final PodcastServiceImpl podcastService;
    private final PodcastMapper podcastMapper;
    private final MinioService minioService;

    public PodcastController(PodcastServiceImpl podcastService, PodcastMapper podcastMapper, MinioService minioService) {
        this.podcastService = podcastService;
        this.podcastMapper = podcastMapper;
        this.minioService = minioService;
    }

    @GetMapping("/")
    public @Valid List<PodcastResponse> getPodcasts(@RequestParam(required = false, defaultValue = "0") @Min(0) @Max(1000) int page,
                                                    @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(1000) int size,
                                                    @RequestParam(required = false, defaultValue = "ID_ASC") PodcastSort sortParam)  {
        return podcastMapper.mapItemsToResponses(
                podcastService.getAll(PageRequest.of(page, size, sortParam.getSortValue()))
        );
    }

    @GetMapping("/{id}")
    public PodcastResponse getPodcast(@PathVariable @NotNull UUID id) {
        return podcastMapper.mapItemToResponse(podcastService.getById(id));
    }

    @PostMapping("/")
    public void createPodcast(@Valid @RequestBody PodcastRequest podcastRequest) {
        podcastService.save(podcastMapper.mapRequestToItem(podcastRequest));
    }

    @DeleteMapping("/{id}")
    public void deletePodcast(@PathVariable @NotNull UUID id) {
        podcastService.delete(id);
    }

    @PutMapping("/{id}")
    public @Valid PodcastResponse updatePodcast(@PathVariable @NotNull UUID id, @Valid @RequestBody PodcastRequest podcastRequest) {
        return podcastMapper.mapItemToResponse(podcastService.update(id, podcastMapper.mapRequestToItem(podcastRequest)));
    }

    @GetMapping("/search/{name}")
    public @Valid List<PodcastResponse> searchPodcasts(@PathVariable @NotNull String name) {
        return podcastService.findByName(name).stream().map(podcastMapper::mapItemToResponse).toList();
    }

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public void updload(@RequestParam("file") MultipartFile file) {
        minioService.uploadFile(file);
    }

    @GetMapping("/download")
    public String getFile(String filename) {
        return minioService.getFile(filename);
    }

}

package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.dto.request.PodcastRequest;
import com.edu.tiktalk_backend.dto.response.PodcastResponse;
import com.edu.tiktalk_backend.mapper.PodcastMapper;
import com.edu.tiktalk_backend.service.PodcastService;
import com.edu.tiktalk_backend.sort_enum.PodcastSort;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import com.edu.tiktalk_backend.service.impl.PodcastServiceImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/podcast")
public class PodcastController {
    private final PodcastService podcastService;
    private final PodcastMapper podcastMapper;

    public PodcastController(PodcastService podcastService, PodcastMapper podcastMapper) {
        this.podcastService = podcastService;
        this.podcastMapper = podcastMapper;
    }

    @Operation(summary = "Получить все подкасты")
    @GetMapping("/")
    public @Valid List<PodcastResponse> getPodcasts(@RequestParam(required = false, defaultValue = "0") @Min(0) @Max(1000) int page,
                                                    @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(1000) int size,
                                                    @RequestParam(required = false, defaultValue = "ID_ASC") PodcastSort sortParam)  {
        return podcastMapper.mapItemsToResponses(
                podcastService.getAll(PageRequest.of(page, size, sortParam.getSortValue()))
        );
    }

    @Operation(summary = "Получить подкаст")
    @GetMapping("/{id}")
    public PodcastResponse getPodcast(@PathVariable @NotNull UUID id) {
        return podcastMapper.mapItemToResponse(podcastService.getById(id));
    }

    @Operation(summary = "Создать подкаст")
    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public UUID createPodcast(@Valid @RequestPart PodcastRequest podcastRequest,
                                         @RequestPart(value = "audio") MultipartFile audio,
                                         @RequestPart(value = "image") MultipartFile image) {
        return podcastService.save(podcastMapper.mapRequestToItem(podcastRequest), audio, image);
    }

    @Operation(summary = "Удалить подкаст")
    @DeleteMapping("/{id}")
    public void deletePodcast(@PathVariable @NotNull UUID id) {
        podcastService.delete(id);
    }

    @Operation(summary = "Обновить подкаст")
    @PutMapping("/{id}")
    public @Valid PodcastResponse updatePodcast(@PathVariable @NotNull UUID id, @Valid @RequestBody PodcastRequest podcastRequest) {
        return podcastMapper.mapItemToResponse(podcastService.update(id, podcastMapper.mapRequestToItem(podcastRequest)));
    }

    @Operation(summary = "Найти подкаст по названию")
    @GetMapping("/search/{name}")
    public @Valid List<PodcastResponse> searchPodcasts(
            @RequestParam(required = false, defaultValue = "0") @Min(0) @Max(1000) int page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(1000) int size,
            @RequestParam(required = false, defaultValue = "ID_ASC") PodcastSort sortParam,
            @PathVariable @NotNull String name) {
        return podcastService.findByName(PageRequest.of(page, size, sortParam.getSortValue()), name).stream().map(podcastMapper::mapItemToResponse).toList();
    }

    @Operation(summary = "Забанить подкаст")
    @PostMapping("/ban/podcast/{id}")
    public void banPodcast(@PathVariable UUID id, @RequestBody @NotBlank @Size(min = 1, max = 1024) String verdict) {
        podcastService.banPodcast(id, verdict);
    }

    @Operation(summary = "Отклонить жалобы на подкаст")
    @PostMapping("/reject/podcast/{id}")
    public void rejectReports(@PathVariable UUID id, @RequestBody @NotBlank @Size(min = 1, max = 1024) String verdict) {
        podcastService.rejectReports(id, verdict);
    }
}

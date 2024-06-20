package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.dto.EmptyResponse;
import com.edu.tiktalk_backend.dto.request.PodcastRequest;
import com.edu.tiktalk_backend.dto.request.ReportRequest;
import com.edu.tiktalk_backend.dto.response.PodcastResponse;
import com.edu.tiktalk_backend.mapper.PodcastMapper;
import com.edu.tiktalk_backend.mapper.ReportMapper;
import com.edu.tiktalk_backend.service.PodcastService;
import com.edu.tiktalk_backend.enums.PodcastSort;
import com.edu.tiktalk_backend.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/podcast")
@SecurityRequirement(name = "Keycloak")
@RequiredArgsConstructor
public class PodcastController {
    private final PodcastService podcastService;
    private final PodcastMapper podcastMapper;
    private final ReportMapper reportMapper;
    private final JwtUtil jwtUtil;

    @Operation(summary = "Лента подкастов")
    @GetMapping("/")
    public @Valid List<PodcastResponse> getPodcasts(
            @RequestParam(required = false, defaultValue = "0") @Min(0) @Max(1000) int page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(1000) int size,
            @RequestParam(required = false, defaultValue = "LIKE_DESK") PodcastSort sortParam)  {
        return podcastMapper.mapItemsToResponses(
                podcastService.getAll(PageRequest.of(page, size, sortParam.getSortValue()))
        );
    }

    @Operation(summary = "Лента подписок")
    @GetMapping("/subscribed/")
    @PreAuthorize("hasRole('USER')")
    public @Valid List<PodcastResponse> getSubscribedPodcasts(
            @RequestParam(required = false, defaultValue = "0") @Min(0) @Max(1000) int page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(1000) int size,
            @RequestParam(required = false, defaultValue = "CREATION_DATE_ASC") PodcastSort sortParam,
            @AuthenticationPrincipal Jwt jwt) {
        return podcastMapper.mapItemsToResponses(
                podcastService.getSubscribedPodcasts(
                        PageRequest.of(page, size, sortParam.getSortValue()),
                        jwtUtil.getIdFromToken(jwt)
                )
        );
    }

    @Operation(summary = "Информация о подкасте")
    @GetMapping("/{id}")
    public PodcastResponse getPodcast(@PathVariable @NotNull UUID id) {
        return podcastMapper.mapItemToResponse(podcastService.getById(id));
    }

    @Operation(summary = "Создать подкаст")
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/")
    public UUID createPodcast(@Valid @RequestBody PodcastRequest podcastRequest,
                              @AuthenticationPrincipal Jwt jwt) {
        return podcastService.save(jwtUtil.getIdFromToken(jwt), podcastMapper.mapRequestToItem(podcastRequest));
    }

    @Operation(summary = "Загрузить изображение")
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/upload-image/{podcastId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public EmptyResponse uploadImage(
            @PathVariable @NotNull UUID podcastId,
            @RequestPart(value = "image") MultipartFile image,
            @AuthenticationPrincipal Jwt jwt) {
        podcastService.uploadImage(jwtUtil.getIdFromToken(jwt), podcastId, image);
        return new EmptyResponse();
    }

    @Operation(summary = "Загрузить аудио")
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/upload-audio/{podcastId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public EmptyResponse uploadAudio(
            @PathVariable @NotNull UUID podcastId,
            @RequestPart(value = "audio") MultipartFile audio,
            @AuthenticationPrincipal Jwt jwt) {
        podcastService.uploadAudio(jwtUtil.getIdFromToken(jwt), podcastId, audio);
        return new EmptyResponse();
    }

    @Operation(summary = "Удалить подкаст")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public EmptyResponse deletePodcast(@PathVariable @NotNull UUID id,
                              @AuthenticationPrincipal Jwt jwt) {
        podcastService.delete(jwtUtil.getIdFromToken(jwt), id);
        return new EmptyResponse();
    }

    @Operation(summary = "Редактировать подкаст")
    @PutMapping("/{podcastId}")
    @PreAuthorize("hasRole('USER')")
    public @Valid PodcastResponse updatePodcast(
            @PathVariable @NotNull UUID podcastId,
            @Valid @RequestBody PodcastRequest podcastRequest,
            @AuthenticationPrincipal Jwt jwt) {
        return podcastMapper.mapItemToResponse(podcastService.update(
                jwtUtil.getIdFromToken(jwt), podcastId, podcastMapper.mapRequestToItem(podcastRequest)));
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
    @PostMapping("/ban/podcast/{podcastId}")
    @PreAuthorize("hasRole('ADMIN')")
    public UUID banPodcast(@PathVariable UUID podcastId, @RequestParam @NotBlank @Size(min = 1, max = 1024) String verdict) {
        return podcastService.banPodcast(podcastId, verdict);
    }

    @Operation(summary = "Отклонить жалобы на подкаст")
    @PostMapping("/reject/{podcast_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UUID rejectReports(@PathVariable UUID podcast_id, @RequestParam @NotBlank @Size(min = 1, max = 1024) String verdict) {
        return podcastService.rejectReports(podcast_id, verdict);
    }

    @Operation(summary = "Пожаловаться на подкаст")
    @PostMapping("/report/")
    @PreAuthorize("hasRole('USER')")
    public UUID createReport(@Valid @RequestBody ReportRequest reportRequest,
                             @AuthenticationPrincipal Jwt jwt) {
        return podcastService.reportPodcast(jwtUtil.getIdFromToken(jwt), reportMapper.mapRequestToItem(reportRequest));
    }
}

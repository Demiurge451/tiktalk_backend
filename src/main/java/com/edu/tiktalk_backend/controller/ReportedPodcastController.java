package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.dto.response.ReportedPodcastResponse;
import com.edu.tiktalk_backend.mapper.ReportedPodcastMapper;
import com.edu.tiktalk_backend.service.impl.ReportedPodcastServiceImpl;
import com.edu.tiktalk_backend.sort_enum.ReportedPodcastSort;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/reported-podcast")
public class ReportedPodcastController {
    private final ReportedPodcastServiceImpl reportedPodcastService;
    private final ReportedPodcastMapper reportedPodcastMapper;

    public ReportedPodcastController(ReportedPodcastServiceImpl reportedPodcastService, ReportedPodcastMapper reportedPodcastMapper) {
        this.reportedPodcastService = reportedPodcastService;
        this.reportedPodcastMapper = reportedPodcastMapper;
    }


    @Operation(summary = "Получить все обжалованные подкасты")
    @GetMapping("/")
    public @Valid List<ReportedPodcastResponse> getReportedPodcasts(@RequestParam(required = false, defaultValue = "0") @Min(0) @Max(1000) int page,
                                                                    @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(1000) int size,
                                                                    @RequestParam(required = false, defaultValue = "CREATION_DATE_ASC") ReportedPodcastSort sortParam)  {
        return reportedPodcastMapper.mapItemsToResponses(
                reportedPodcastService.getAll(PageRequest.of(page, size, sortParam.getSortValue()))
        );
    }

    @Operation(summary = "Получить обжалованный подкаст")
    @GetMapping("/{id}")
    public ReportedPodcastResponse getPodcast(@PathVariable @NotNull UUID id) {
        return reportedPodcastMapper.mapItemToResponse(reportedPodcastService.getById(id));
    }

    @Operation(summary = "Удалить обжалованный подкаст")
    @DeleteMapping("/{id}")
    public void deletePodcast(@PathVariable @NotNull UUID id) {
        reportedPodcastService.delete(id);
    }
}

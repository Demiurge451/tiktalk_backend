package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.dto.EmptyResponse;
import com.edu.tiktalk_backend.dto.response.ReportedPodcastResponse;
import com.edu.tiktalk_backend.mapper.ReportedPodcastMapper;
import com.edu.tiktalk_backend.service.impl.ReportedPodcastServiceImpl;
import com.edu.tiktalk_backend.enums.ReportedPodcastSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/reported-podcast")
@SecurityRequirement(name = "Keycloak")
@RequiredArgsConstructor
public class ReportedPodcastController {
    private final ReportedPodcastServiceImpl reportedPodcastService;
    private final ReportedPodcastMapper reportedPodcastMapper;

    @Operation(summary = "Список обжалованных подкастов")
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public @Valid List<ReportedPodcastResponse> getReportedPodcasts(@RequestParam(required = false, defaultValue = "0") @Min(0) @Max(1000) int page,
                                                                    @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(1000) int size,
                                                                    @RequestParam(required = false, defaultValue = "CREATION_DATE_ASC") ReportedPodcastSort sortParam)  {
        return reportedPodcastMapper.mapItemsToResponses(
                reportedPodcastService.getAll(PageRequest.of(page, size, sortParam.getSortValue()))
        );
    }

    @Operation(summary = "Информация об обжалованном подкасте")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ReportedPodcastResponse getPodcast(@PathVariable @NotNull UUID id) {
        return reportedPodcastMapper.mapItemToResponse(reportedPodcastService.getById(id));
    }

    @Operation(summary = "Удалить обжалованный подкаст")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EmptyResponse deletePodcast(@PathVariable @NotNull UUID id) {
        reportedPodcastService.delete(id);
        return new EmptyResponse();
    }
}

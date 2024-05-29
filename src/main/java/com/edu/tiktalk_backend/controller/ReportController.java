package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.dto.response.ReportResponse;
import com.edu.tiktalk_backend.mapper.ReportMapper;
import com.edu.tiktalk_backend.service.ReportService;
import com.edu.tiktalk_backend.enums.ReportSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/report")
@SecurityRequirement(name = "Keycloak")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final ReportMapper reportMapper;

    @Operation(summary = "Получить все жалобы")
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public @Valid List<ReportResponse> getReports(@RequestParam(required = false, defaultValue = "0") @Min(0) @Max(1000) int page,
                                                  @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(1000) int size,
                                                  @RequestParam(required = false, defaultValue = "ID_ASC") ReportSort sortParam)  {
        return reportMapper.mapItemsToResponses(
                reportService.getAll(PageRequest.of(page, size, sortParam.getSortValue()))
        );
    }

    @Operation(summary = "Получить жалобу")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public @Valid ReportResponse getReport(@PathVariable @NotNull UUID id) {
        return reportMapper.mapItemToResponse(reportService.getById(id));
    }

    @Operation(summary = "Получить все жалобы на подкаст")
    @GetMapping("/reports/by-podcast/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ReportResponse> getAllByPodcast(
            @RequestParam(required = false, defaultValue = "0") @Min(0) @Max(1000) int page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(1000) int size,
            @RequestParam(required = false, defaultValue = "ID_ASC") ReportSort sortParam,
            @PathVariable @NotNull UUID id) {
        return reportMapper.mapItemsToResponses(
                reportService.getAllByPodcast(PageRequest.of(page, size, sortParam.getSortValue()), id)
        );
    }
}

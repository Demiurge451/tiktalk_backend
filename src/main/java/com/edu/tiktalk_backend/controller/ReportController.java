package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.dto.request.ReportRequest;
import com.edu.tiktalk_backend.dto.response.ReportResponse;
import com.edu.tiktalk_backend.mapper.ReportMapper;
import com.edu.tiktalk_backend.model.Report;
import com.edu.tiktalk_backend.service.CrudService;
import com.edu.tiktalk_backend.service.ReportService;
import com.edu.tiktalk_backend.sort_enum.ReportSort;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService reportService;
    private final ReportMapper reportMapper;

    public ReportController(ReportService reportService, ReportMapper reportMapper) {
        this.reportService = reportService;
        this.reportMapper = reportMapper;
    }

    @Operation(summary = "Получить все жалобы")
    @GetMapping("/")
    public @Valid List<ReportResponse> getReports(@RequestParam(required = false, defaultValue = "0") @Min(0) @Max(1000) int page,
                                                  @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(1000) int size,
                                                  @RequestParam(required = false, defaultValue = "ID_ASC") ReportSort sortParam)  {
        return reportMapper.mapItemsToResponses(
                reportService.getAll(PageRequest.of(page, size, sortParam.getSortValue()))
        );
    }

    @Operation(summary = "Получить жалобу")
    @GetMapping("/{id}")
    public @Valid ReportResponse getReport(@PathVariable @NotNull UUID id) {
        return reportMapper.mapItemToResponse(reportService.getById(id));
    }

    @Operation(summary = "Создать жалобу")
    @PostMapping("/")
    public UUID createReport(@Valid @RequestBody ReportRequest reportRequest) {
        return reportService.save(reportMapper.mapRequestToItem(reportRequest));
    }

    @Operation(summary = "Получить все жалобы на подкаст")
    @GetMapping("/reports/by-podcast/{id}")
    public List<ReportResponse> getAllByPodcast(
            @RequestParam(required = false, defaultValue = "0") @Min(0) @Max(1000) int page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(1000) int size,
            @RequestParam(required = false, defaultValue = "ID_ASC") ReportSort sortParam,
            @NotNull UUID id) {
        return reportMapper.mapItemsToResponses(
                reportService.getAllByPodcast(PageRequest.of(page, size, sortParam.getSortValue()), id)
        );
    }
}

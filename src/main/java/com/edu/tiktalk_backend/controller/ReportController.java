package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.dto.request.ReportRequest;
import com.edu.tiktalk_backend.dto.response.ReportResponse;
import com.edu.tiktalk_backend.mapper.ReportMapper;
import com.edu.tiktalk_backend.model.Report;
import com.edu.tiktalk_backend.service.CrudService;
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
    private final CrudService<Report, UUID> reportService;
    private final ReportMapper reportMapper;

    public ReportController(CrudService<Report, UUID> reportService, ReportMapper reportMapper) {
        this.reportService = reportService;
        this.reportMapper = reportMapper;
    }

    @GetMapping("/")
    public @Valid List<ReportResponse> getReports(@RequestParam(required = false, defaultValue = "0") @Min(0) @Max(1000) int page,
                                                  @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(1000) int size,
                                                  @RequestParam(required = false, defaultValue = "id") @NotBlank @Size(max = 50) String sortParam)  {
        return reportMapper.mapItemsToResponses(
                reportService.getAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortParam)))
        );
    }

    @GetMapping("/{id}")
    public @Valid ReportResponse getReport(@PathVariable @NotNull UUID id) {
        return reportMapper.mapItemToResponse(reportService.getById(id));
    }

    @PostMapping("/")
    public void createReport(@Valid @RequestBody ReportRequest reportRequest) {
        reportService.save(reportMapper.mapRequestToItem(reportRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable @NotNull UUID id) {
        reportService.delete(id);
    }

    @PutMapping("/{id}")
    public @Valid ReportResponse updateReport(@PathVariable @NotNull UUID id, @Valid @RequestBody ReportRequest reportRequest) {
        return reportMapper.mapItemToResponse(reportService.update(id, reportMapper.mapRequestToItem(reportRequest)));
    }
}

package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.dto.request.ReportRequest;
import com.edu.tiktalk_backend.dto.response.ReportResponse;
import com.edu.tiktalk_backend.mapper.ReportMapper;
import com.edu.tiktalk_backend.model.Report;
import com.edu.tiktalk_backend.service.CrudService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    private final CrudService<Report, UUID> reportService;
    private final ReportMapper reportMapper;

    public ReportController(@Qualifier("reportServiceImpl") CrudService<Report, UUID> reportService, ReportMapper reportMapper) {
        this.reportService = reportService;
        this.reportMapper = reportMapper;
    }

    @GetMapping("/")
    public List<ReportResponse> getReports(@RequestParam(required = false, defaultValue = "0") int page,
                                           @RequestParam(required = false, defaultValue = "10") int size,
                                           @RequestParam(required = false, defaultValue = "id") String sortParam) {
        return reportService.getListOfItems(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortParam)))
                .stream().map(reportMapper::mapItemToResponse).toList();
    }

    @GetMapping("/{id}")
    public ReportResponse getReport(@PathVariable UUID id) {
        return reportMapper.mapItemToResponse(reportService.getById(id));
    }

    @PostMapping("/")
    public void createReport(@RequestBody ReportRequest reportRequest) {
        reportService.save(reportMapper.mapRequestToItem(reportRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable UUID id) {
        reportService.delete(id);
    }

    @PutMapping("/{id}")
    public ReportResponse updateReport(@PathVariable UUID id, @RequestBody ReportRequest reportRequest) {
        return reportMapper.mapItemToResponse(reportService.update(id, reportMapper.mapRequestToItem(reportRequest)));
    }
}

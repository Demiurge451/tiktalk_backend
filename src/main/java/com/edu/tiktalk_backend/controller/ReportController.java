package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.model.Podcast;
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

    public ReportController(@Qualifier("reportServiceImpl") CrudService<Report, UUID> reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/")
    public List<Report> getReports(@RequestParam(required = false, defaultValue = "0") int page,
                                   @RequestParam(required = false, defaultValue = "10") int size,
                                   @RequestParam(required = false, defaultValue = "id") String sortParam) {
        return reportService.getListOfItems(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortParam)));
    }

    @GetMapping("/{id}")
    public Report getReport(@PathVariable UUID id) {
        return reportService.getById(id);
    }

    @PostMapping("/")
    public void createReport(@RequestBody Report report) {
        reportService.save(report);
    }

    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable UUID id) {
        reportService.delete(id);
    }

    @PutMapping("/{id}")
    public Podcast updateReport(@PathVariable UUID id, @RequestBody Report report) {
        return null;
    }
}

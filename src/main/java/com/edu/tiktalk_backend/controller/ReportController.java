package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.model.Podcast;
import com.edu.tiktalk_backend.model.Report;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    @GetMapping("/")
    public List<Report> getReports() {
        return null;
    }

    @GetMapping("/{id}")
    public Report getReport(@PathVariable UUID id) {
        return null;
    }

    @PostMapping("/")
    public void createReport(@RequestBody Report report) {

    }

    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable UUID id) {

    }

    @PutMapping("/{id}")
    public Podcast updateReport(@PathVariable UUID id, @RequestBody Report report) {
        return null;
    }
}

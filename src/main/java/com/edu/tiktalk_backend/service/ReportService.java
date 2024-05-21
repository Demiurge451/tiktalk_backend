package com.edu.tiktalk_backend.service;

import com.edu.tiktalk_backend.model.Report;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface ReportService {
    List<Report> getAll(PageRequest pageRequest);
    Report getById(UUID id);
    UUID save(Report item);
    List<Report> getAllByPodcast(PageRequest pageRequest, UUID id);
}

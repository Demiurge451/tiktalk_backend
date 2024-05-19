package com.edu.tiktalk_backend.service;

import com.edu.tiktalk_backend.model.Report;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface ReportService {
    List<Report> getAll(PageRequest pageRequest);
    Report getById(UUID id);
    Report save(Report item);
}

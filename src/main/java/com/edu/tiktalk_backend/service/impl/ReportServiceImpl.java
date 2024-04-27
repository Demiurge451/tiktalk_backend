package com.edu.tiktalk_backend.service.impl;

import com.edu.tiktalk_backend.exception.NotFoundException;
import com.edu.tiktalk_backend.mapper.ReportMapper;
import com.edu.tiktalk_backend.model.Report;
import com.edu.tiktalk_backend.repository.ReportRepository;
import com.edu.tiktalk_backend.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportServiceImpl implements CrudService<Report, UUID> {
    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    @Override
    public List<Report> getAll(PageRequest pageRequest) {
        return reportRepository.findAll(pageRequest).getContent();
    }

    @Override
    public Report getById(UUID id) {
        return reportRepository.findById(id).orElseThrow(() -> new NotFoundException("Person not found"));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        reportRepository.delete(getById(id));
    }

    @Override
    @Transactional
    public void save(Report report) {
        reportRepository.save(report);
    }

    @Override
    @Transactional
    public Report update(UUID id, Report item) {
        Report report = getById(id);
        reportMapper.updateReport(item, report);
        return reportRepository.save(report);
    }
}

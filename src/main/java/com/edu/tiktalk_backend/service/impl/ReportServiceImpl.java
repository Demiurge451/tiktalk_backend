package com.edu.tiktalk_backend.service.impl;

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
    @Override
    public List<Report> getListOfItems(PageRequest pageRequest) {
        return null;
    }

    @Override
    public Report getById(UUID uuid) {
        return null;
    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public void save(Report item) {

    }

    @Override
    public Report update(UUID uuid, Report item) {
        return null;
    }
}

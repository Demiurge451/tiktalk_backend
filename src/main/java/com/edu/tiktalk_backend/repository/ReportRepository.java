package com.edu.tiktalk_backend.repository;

import com.edu.tiktalk_backend.model.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {
    Page<Report> findAllByReporterId(Pageable page, UUID personId);
}
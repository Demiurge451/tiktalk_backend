package com.edu.tiktalk_backend.repository;

import com.edu.tiktalk_backend.model.ReportedPodcast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReportedPodcastRepository extends JpaRepository<ReportedPodcast, UUID> {
}

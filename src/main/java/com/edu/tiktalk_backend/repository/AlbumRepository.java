package com.edu.tiktalk_backend.repository;

import com.edu.tiktalk_backend.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AlbumRepository extends JpaRepository<Album, UUID> {
}
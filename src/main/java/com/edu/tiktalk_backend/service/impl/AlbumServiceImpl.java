package com.edu.tiktalk_backend.service.impl;

import com.edu.tiktalk_backend.exception.NotFoundException;
import com.edu.tiktalk_backend.mapper.AlbumMapper;
import com.edu.tiktalk_backend.model.Album;
import com.edu.tiktalk_backend.repository.AlbumRepository;
import com.edu.tiktalk_backend.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;

    @Override
    public List<Album> getAll(PageRequest pageRequest) {
        return albumRepository.findAll(pageRequest).getContent();
    }

    @Override
    public Album getById(UUID id) {
        return albumRepository.findById(id).orElseThrow(() -> new NotFoundException("Album not found"));
    }

    @Override
    @Transactional
    public void delete(UUID loginId, UUID albumId) {
        checkBelong(loginId, albumId);
        albumRepository.deleteById(albumId);
    }

    @Override
    @Transactional
    public UUID save(UUID loginId, Album album) {
        album.setPersonId(loginId);
        Album savedAlbum = albumRepository.save(album);
        return savedAlbum.getId();
    }

    @Override
    @Transactional
    public Album update(UUID loginId, UUID albumId, Album item) {
        checkBelong(loginId, albumId);
        item.setPersonId(loginId);
        Album album = getById(albumId);
        albumMapper.updateAlbum(item, album);
        return albumRepository.save(album);
    }

    @Override
    public void checkBelong(UUID loginId, UUID albumId) {
        if (!getById(albumId).getPersonId().equals(loginId)) {
            throw new RuntimeException("this album does not belong to this person");
        }
    }
}
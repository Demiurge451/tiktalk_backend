package com.edu.tiktalk_backend.service.impl;

import com.edu.tiktalk_backend.exception.NotFoundException;
import com.edu.tiktalk_backend.mapper.AlbumMapper;
import com.edu.tiktalk_backend.model.Album;
import com.edu.tiktalk_backend.repository.AlbumRepository;
import com.edu.tiktalk_backend.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlbumServiceImpl implements CrudService<Album, UUID> {
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
    public void delete(UUID id) {
        albumRepository.delete(getById(id));
    }

    @Override
    @Transactional
    public void save(Album album) {
        albumRepository.save(album);
    }

    @Override
    @Transactional
    public Album update(UUID id, Album item) {
        Album album = getById(id);
        albumMapper.updateAlbum(item, album);
        return albumRepository.save(album);
    }
}
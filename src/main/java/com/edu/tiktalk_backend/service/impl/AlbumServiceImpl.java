package com.edu.tiktalk_backend.service.impl;

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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlbumServiceImpl implements CrudService<Album, UUID> {
    private final AlbumRepository AlbumRepository;


    @Override
    public List<Album> getListOfItems(PageRequest pageRequest) {
        return null;
    }

    @Override
    public Album getById(UUID uuid) {
        return null;
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {

    }

    @Override
    @Transactional
    public void save(Album item) {

    }

    @Override
    @Transactional
    public Album update(UUID uuid, Album item) {
        return null;
    }
}
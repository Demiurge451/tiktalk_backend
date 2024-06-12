package com.edu.tiktalk_backend.service;

import com.edu.tiktalk_backend.model.Album;

import java.util.List;
import java.util.UUID;

public interface AlbumService extends CrudService<Album, UUID> {
    List<Album> getMyAlbums(UUID loginId);
}

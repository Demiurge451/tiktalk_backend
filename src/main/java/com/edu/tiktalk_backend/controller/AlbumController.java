package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.model.Album;
import com.edu.tiktalk_backend.model.Person;
import com.edu.tiktalk_backend.service.CrudService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/album")
public class AlbumController {
    private final CrudService<Album, UUID> albumService;

    public AlbumController(@Qualifier("albumServiceImpl") CrudService<Album, UUID> albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/")
    public List<Album> getAlbums(@RequestParam(required = false, defaultValue = "0") int page,
                                 @RequestParam(required = false, defaultValue = "10") int size,
                                 @RequestParam(required = false, defaultValue = "id") String sortParam) {
        return albumService.getListOfItems(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortParam)));
    }

    @GetMapping("/{id}")
    public Album getAlbum(@PathVariable UUID id) {
        return albumService.getById(id);
    }

    @PostMapping("/")
    public void createAlbum(@RequestBody Album album) {
        albumService.save(album);
    }

    @DeleteMapping("/{id}")
    public void deleteAlbum(@PathVariable UUID id) {
        albumService.delete(id);
    }

    @PutMapping("/{id}")
    public Album updateAlbum(@PathVariable UUID id, @RequestBody Album album) {
        return null;
    }
}

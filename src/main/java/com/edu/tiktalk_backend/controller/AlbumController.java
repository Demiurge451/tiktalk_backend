package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.model.Album;
import com.edu.tiktalk_backend.model.Person;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/album")
public class AlbumController {
    @GetMapping("/")
    public List<Album> getAlbums() {
        return null;
    }

    @GetMapping("/{id}")
    public Album getAlbum(@PathVariable UUID id) {
        return null;
    }

    @PostMapping("/")
    public void createAlbum(@RequestBody Album album) {

    }

    @DeleteMapping("/{id}")
    public void deleteAlbum(@PathVariable UUID id) {

    }

    @PutMapping("/{id}")
    public Album updateAlbum(@PathVariable UUID id, @RequestBody Album album) {
        return null;
    }
}

package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.dto.request.AlbumRequest;
import com.edu.tiktalk_backend.dto.response.AlbumResponse;
import com.edu.tiktalk_backend.mapper.AlbumMapper;
import com.edu.tiktalk_backend.model.Album;
import com.edu.tiktalk_backend.service.CrudService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/album")
public class AlbumController {
    private final CrudService<Album, UUID> albumService;
    private final AlbumMapper albumMapper;

    public AlbumController(CrudService<Album, UUID> albumService, AlbumMapper albumMapper) {
        this.albumService = albumService;
        this.albumMapper = albumMapper;
    }

    @GetMapping("/")
    public List<AlbumResponse> getAlbums(@RequestParam(required = false, defaultValue = "0") int page,
                                         @RequestParam(required = false, defaultValue = "10") int size,
                                         @RequestParam(required = false, defaultValue = "id") String sortParam) {
        return albumService.getAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortParam)))
                .stream().map(albumMapper::mapItemToResponse).toList();
    }

    @GetMapping("/{id}")
    public AlbumResponse getAlbum(@PathVariable UUID id) {
        return albumMapper.mapItemToResponse(albumService.getById(id));
    }

    @PostMapping("/")
    public void createAlbum(@RequestBody AlbumRequest albumRequest) {
        albumService.save(albumMapper.mapRequestToItem(albumRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteAlbum(@PathVariable UUID id) {
        albumService.delete(id);
    }

    @PutMapping("/{id}")
    public AlbumResponse updateAlbum(@PathVariable UUID id, @RequestBody AlbumRequest albumRequest) {
        return albumMapper.mapItemToResponse(albumService.update(id, albumMapper.mapRequestToItem(albumRequest)));
    }
}

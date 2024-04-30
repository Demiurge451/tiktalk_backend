package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.dto.request.AlbumRequest;
import com.edu.tiktalk_backend.dto.response.AlbumResponse;
import com.edu.tiktalk_backend.mapper.AlbumMapper;
import com.edu.tiktalk_backend.model.Album;
import com.edu.tiktalk_backend.service.CrudService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/album")
public class AlbumController {
    private final CrudService<Album, UUID> albumService;
    private final AlbumMapper albumMapper;

    public AlbumController(@Qualifier("albumServiceImpl") CrudService<Album, UUID> albumService, AlbumMapper albumMapper) {
        this.albumService = albumService;
        this.albumMapper = albumMapper;
    }

    @GetMapping("/")
    public @Valid List<AlbumResponse> getAlbums(@RequestParam(required = false, defaultValue = "0") @Min(0) @Max(1000) int page,
                                                @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(1000) int size,
                                                @RequestParam(required = false, defaultValue = "id") @NotBlank @Size(min = 1, max = 50) String sortParam) {
        return albumMapper.mapItemsToResponses(
                albumService.getAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortParam)))
        );
    }

    @GetMapping("/{id}")
    public @Valid AlbumResponse getAlbum(@PathVariable @NotNull UUID id) {
        return albumMapper.mapItemToResponse(albumService.getById(id));
    }

    @PostMapping("/")
    public void createAlbum(@Valid @RequestBody AlbumRequest albumRequest) {
        albumService.save(albumMapper.mapRequestToItem(albumRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteAlbum(@PathVariable @NotNull UUID id) {
        albumService.delete(id);
    }

    @PutMapping("/{id}")
    public @Valid AlbumResponse updateAlbum(@PathVariable @NotNull UUID id, @Valid @RequestBody AlbumRequest albumRequest) {
        return albumMapper.mapItemToResponse(albumService.update(id, albumMapper.mapRequestToItem(albumRequest)));
    }
}

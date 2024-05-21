package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.dto.request.AlbumRequest;
import com.edu.tiktalk_backend.dto.response.AlbumResponse;
import com.edu.tiktalk_backend.mapper.AlbumMapper;
import com.edu.tiktalk_backend.model.Album;
import com.edu.tiktalk_backend.service.CrudService;
import com.edu.tiktalk_backend.sort_enum.AlbumSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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

    public AlbumController(CrudService<Album, UUID> albumService, AlbumMapper albumMapper) {
        this.albumService = albumService;
        this.albumMapper = albumMapper;
    }

    @Operation(summary = "Получить все альбомы")
    @GetMapping("/")
    public @Valid List<AlbumResponse> getAlbums(@RequestParam(required = false, defaultValue = "0") @Min(0) @Max(1000) int page,
                                                @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(1000) int size,
                                                @RequestParam(required = false, defaultValue = "ID_ASC") AlbumSort sortParam) {
        return albumMapper.mapItemsToResponses(
                albumService.getAll(PageRequest.of(page, size, sortParam.getSortValue()))
        );
    }

    @Operation(summary = "Получить альбом")
    @GetMapping("/{id}")
    public @Valid AlbumResponse getAlbum(@PathVariable @NotNull UUID id) {
        return albumMapper.mapItemToResponse(albumService.getById(id));
    }

    @Operation(summary = "Создать альбом")
    @PostMapping("/")
    public UUID createAlbum(@Valid @RequestBody AlbumRequest albumRequest) {
        return albumService.save(albumMapper.mapRequestToItem(albumRequest));
    }

    @Operation(summary = "Удалить альбом")
    @DeleteMapping("/{id}")
    public void deleteAlbum(@PathVariable @NotNull UUID id) {
        albumService.delete(id);
    }

    @Operation(summary = "Обновить альбом")
    @PutMapping("/{id}")
    public @Valid AlbumResponse updateAlbum(@PathVariable @NotNull UUID id, @Valid @RequestBody AlbumRequest albumRequest) {
        return albumMapper.mapItemToResponse(albumService.update(id, albumMapper.mapRequestToItem(albumRequest)));
    }
}

package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.dto.request.AlbumRequest;
import com.edu.tiktalk_backend.dto.response.AlbumResponse;
import com.edu.tiktalk_backend.mapper.AlbumMapper;
import com.edu.tiktalk_backend.model.Album;
import com.edu.tiktalk_backend.enums.AlbumSort;
import com.edu.tiktalk_backend.service.AlbumService;
import com.edu.tiktalk_backend.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/album")
@SecurityRequirement(name = "Keycloak")
@RequiredArgsConstructor
public class AlbumController {
    private final AlbumService albumService;
    private final AlbumMapper albumMapper;
    private final JwtUtil jwtUtil;

    @Operation(summary = "Список альбомов")
    @GetMapping("/")
    public @Valid List<AlbumResponse> getAlbums(@RequestParam(required = false, defaultValue = "0") @Min(0) @Max(1000) int page,
                                                @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(1000) int size,
                                                @RequestParam(required = false, defaultValue = "ID_ASC") AlbumSort sortParam) {
        return albumMapper.mapItemsToResponses(
                albumService.getAll(PageRequest.of(page, size, sortParam.getSortValue()))
        );
    }

    @Operation(summary = "Список моих альбомов")
    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    public @Valid List<AlbumResponse> getMyAlbums(@AuthenticationPrincipal Jwt jwt) {
        return albumMapper.mapItemsToResponses(
                albumService.getMyAlbums(jwtUtil.getIdFromToken(jwt))
        );
    }

    @Operation(summary = "Информация об альбоме")
    @GetMapping("/{id}")
    public @Valid AlbumResponse getAlbum(@PathVariable @NotNull UUID id) {
        return albumMapper.mapItemToResponse(albumService.getById(id));
    }

    @Operation(summary = "Создать альбом")
    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    public UUID createAlbum(@Valid @RequestBody AlbumRequest albumRequest,
                            @AuthenticationPrincipal Jwt jwt) {
        return albumService.save(jwtUtil.getIdFromToken(jwt), albumMapper.mapRequestToItem(albumRequest));
    }

    @Operation(summary = "Удалить альбом")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public void deleteAlbum(@PathVariable @NotNull UUID id, @AuthenticationPrincipal Jwt jwt) {
        albumService.delete(jwtUtil.getIdFromToken(jwt), id);
    }

    @Operation(summary = "Редактировать альбом")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public @Valid AlbumResponse updateAlbum(@PathVariable @NotNull UUID id,
                                            @Valid @RequestBody AlbumRequest albumRequest,
                                            @AuthenticationPrincipal Jwt jwt) {
        return albumMapper.mapItemToResponse(albumService.update(jwtUtil.getIdFromToken(jwt), id,
                albumMapper.mapRequestToItem(albumRequest)));
    }
}

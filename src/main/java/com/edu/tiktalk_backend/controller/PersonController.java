package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.dto.request.PersonRequest;
import com.edu.tiktalk_backend.dto.response.PersonResponse;
import com.edu.tiktalk_backend.mapper.PersonMapper;
import com.edu.tiktalk_backend.service.PersonService;
import com.edu.tiktalk_backend.enums.PersonSort;
import com.edu.tiktalk_backend.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/person")
@SecurityRequirement(name = "Keycloak")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;
    private final PersonMapper personMapper;
    private final JwtUtil jwtUtil;

    @Operation(summary = "Получить всех людей")
    @GetMapping("/")
    public @Valid List<PersonResponse> getPersons(@RequestParam(required = false, defaultValue = "0") @Min(0) @Max(1000) int page,
                                                  @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(1000) int size,
                                                  @RequestParam(required = false, defaultValue = "ID_ASC") PersonSort sortParam) {
        return personMapper.mapItemsToResponses(
                personService.getAll(PageRequest.of(page, size, sortParam.getSortValue()))
        );
    }

    @Operation(summary = "Получить человека")
    @GetMapping("/{id}")
    public @Valid PersonResponse getPerson(@PathVariable @NotNull UUID id) {
        return personMapper.mapItemToResponse(personService.getById(id));
    }

    @Operation(summary = "Загрузить аватарку")
    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasRole('USER')")
    public String uploadImage(@RequestPart(name = "image") MultipartFile image,
                            @AuthenticationPrincipal Jwt jwt) {
        return personService.upload(jwtUtil.getIdFromToken(jwt), image);
    }

    @Operation(summary = "Удалить человека")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deletePerson(@PathVariable @NotNull UUID id) {
        personService.delete(id);
    }

    @Operation(summary = "Обновить человека")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public @Valid PersonResponse updatePerson(@PathVariable  @NotNull UUID id, @Valid @RequestBody PersonRequest personRequest) {
        //return personMapper.mapItemToResponse(personService.update(id, personMapper.mapRequestToItem(personRequest)));
        return null;
    }

    @Operation(summary = "Лайкнуть подкаст")
    @PostMapping("/like/{personId}/{podcastId}")
    @PreAuthorize("hasRole('USER')")
    public void likePodcast(@PathVariable @NotNull UUID personId, @PathVariable @NotNull UUID podcastId) {
        personService.like(personId, podcastId);
    }

    @Operation(summary = "Подписаться на человека")
    @PostMapping("/follow/{followerId}/{authorId}")
    @PreAuthorize("hasRole('USER')")
    public void followPerson(@PathVariable @NotNull UUID followerId, @PathVariable @NotNull UUID authorId) {
        personService.follow(followerId, authorId);
    }

    @Operation(summary = "Отписаться от человека")
    @DeleteMapping("/unfollow/{followerId}/{authorId}")
    @PreAuthorize("hasRole('USER')")
    public void unfollowPerson(@PathVariable @NotNull UUID followerId, @PathVariable @NotNull UUID authorId) {
        personService.unfollow(followerId, authorId);
    }

    @Operation(summary = "Убрать лайк с подкаста")
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/unlike/{personId}/{podcastId}")
    public void unlikePodcast(@PathVariable @NotNull UUID personId, @PathVariable @NotNull UUID podcastId) {
        personService.unlike(personId, podcastId);
    }

    @Operation(summary = "Проверка лайка")
    @GetMapping("/is-liked/{personId}/{podcastId}")
    @PreAuthorize("hasRole('USER')")
    public boolean isPodcastLiked(@PathVariable @NotNull UUID personId, @PathVariable @NotNull UUID podcastId) {
        return personService.isPodcastLiked(personId, podcastId);
    }

    @Operation(summary = "Проверка подписки")
    @GetMapping("/is-followed/{followerId}/{authorId}")
    @PreAuthorize("hasRole('USER')")
    public boolean isPersonFollowed(@PathVariable @NotNull UUID followerId, @PathVariable @NotNull UUID authorId) {
        return personService.isPersonFollowed(followerId, authorId);
    }
}

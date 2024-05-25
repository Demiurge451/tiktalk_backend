package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.dto.request.PersonRequest;
import com.edu.tiktalk_backend.dto.response.PersonResponse;
import com.edu.tiktalk_backend.mapper.PersonMapper;
import com.edu.tiktalk_backend.service.PersonService;
import com.edu.tiktalk_backend.service.impl.PersonServiceImpl;
import com.edu.tiktalk_backend.sort_enum.PersonSort;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/person")
public class PersonController {
    private final PersonService personService;
    private final PersonMapper personMapper;

    public PersonController(PersonService personService, PersonMapper personMapper) {
        this.personService = personService;
        this.personMapper = personMapper;
    }

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

    @Operation(summary = "Создать человека")
    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public UUID createPerson(@Valid @RequestPart PersonRequest personRequest,
                                       @RequestPart(name = "image") MultipartFile image) {
        return personService.save(personMapper.mapRequestToItem(personRequest), image);
    }

    @Operation(summary = "Удалить человека")
    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable @NotNull UUID id) {
        personService.delete(id);
    }

    @Operation(summary = "Обновить человека")
    @PutMapping("/{id}")
    public @Valid PersonResponse updatePerson(@PathVariable  @NotNull UUID id, @Valid @RequestBody PersonRequest personRequest) {
        return personMapper.mapItemToResponse(personService.update(id, personMapper.mapRequestToItem(personRequest)));
    }

    @Operation(summary = "Лайкнуть подкаст")
    @PostMapping("/like/{personId}/{podcastId}")
    public void likePodcast(@PathVariable @NotNull UUID personId, @PathVariable @NotNull UUID podcastId) {
        personService.like(personId, podcastId);
    }

    @Operation(summary = "Подписаться на человека")
    @PostMapping("/follow/{followerId}/{authorId}")
    public void followPerson(@PathVariable @NotNull UUID followerId, @PathVariable @NotNull UUID authorId) {
        personService.follow(followerId, authorId);
    }

    @Operation(summary = "Отписаться от человека")
    @DeleteMapping("/unfollow/{followerId}/{authorId}")
    public void unfollowPerson(@PathVariable @NotNull UUID followerId, @PathVariable @NotNull UUID authorId) {
        personService.unfollow(followerId, authorId);
    }

    @Operation(summary = "Убрать лайк с подкаста")
    @DeleteMapping("/unlike/{personId}/{podcastId}")
    public void unlikePodcast(@PathVariable @NotNull UUID personId, @PathVariable @NotNull UUID podcastId) {
        personService.unlike(personId, podcastId);
    }

    @Operation(summary = "Проверка лайка")
    @GetMapping("/isLiked/{personId}/{podcastId}")
    public boolean isPodcastLiked(@PathVariable @NotNull UUID personId, @PathVariable @NotNull UUID podcastId) {
        return personService.isPodcastLiked(personId, podcastId);
    }

    @Operation(summary = "Проверка подписки")
    @GetMapping("/isFollowed/{followerId}/{authorId}")
    public boolean isPersonFollowed(@PathVariable @NotNull UUID followerId, @PathVariable @NotNull UUID authorId) {
        return personService.isPersonFollowed(followerId, authorId);
    }
}

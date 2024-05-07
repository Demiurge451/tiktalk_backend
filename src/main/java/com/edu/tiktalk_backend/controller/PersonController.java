package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.dto.request.PersonRequest;
import com.edu.tiktalk_backend.dto.response.PersonResponse;
import com.edu.tiktalk_backend.mapper.PersonMapper;
import com.edu.tiktalk_backend.service.PersonService;
import com.edu.tiktalk_backend.service.impl.PersonServiceImpl;
import com.edu.tiktalk_backend.sort_enum.PersonSort;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/")
    public @Valid List<PersonResponse> getPersons(@RequestParam(required = false, defaultValue = "0") @Min(0) @Max(1000) int page,
                                                  @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(1000) int size,
                                                  @RequestParam(required = false, defaultValue = "ID_ASC") PersonSort sortParam) {
        return personMapper.mapItemsToResponses(
                personService.getAll(PageRequest.of(page, size, sortParam.getSortValue()))
        );
    }

    @GetMapping("/{id}")
    public @Valid PersonResponse getPerson(@PathVariable @NotNull UUID id) {
        return personMapper.mapItemToResponse(personService.getById(id));
    }

    @PostMapping("/")
    public void createPerson(@Valid @RequestBody PersonRequest personRequest) {
        personService.save(personMapper.mapRequestToItem(personRequest));
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable @NotNull UUID id) {
        personService.delete(id);
    }

    @PutMapping("/{id}")
    public @Valid PersonResponse updatePerson(@PathVariable  @NotNull UUID id, @Valid @RequestBody PersonRequest personRequest) {
        return personMapper.mapItemToResponse(personService.update(id, personMapper.mapRequestToItem(personRequest)));
    }

    @PostMapping("/like/{personId}/{podcastId}")
    public void likePodcast(@PathVariable @NotNull UUID personId, @PathVariable @NotNull UUID podcastId) {
        personService.like(personId, podcastId);
    }

    @PostMapping("/follow/{personId}/{authorId}")
    public void followPerson(@PathVariable @NotNull UUID personId, @PathVariable @NotNull UUID authorId) {
        personService.follow(personId, authorId);
    }

    @DeleteMapping("/unfollow/{personId}/{authorId}")
    public void unfollowPerson(@PathVariable @NotNull UUID personId, @PathVariable @NotNull UUID authorId) {
        personService.unfollow(personId, authorId);
    }

    @DeleteMapping("/unlike/{personId}/{podcastId}")
    public void unlikePodcast(@PathVariable @NotNull UUID personId, @PathVariable @NotNull UUID podcastId) {
        personService.unlike(personId, podcastId);
    }
}

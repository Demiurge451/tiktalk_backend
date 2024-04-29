package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.dto.request.PersonRequest;
import com.edu.tiktalk_backend.dto.response.PersonResponse;
import com.edu.tiktalk_backend.mapper.PersonMapper;
import com.edu.tiktalk_backend.model.Person;
import com.edu.tiktalk_backend.service.CrudService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Qualifier;
import com.edu.tiktalk_backend.service.impl.PersonServiceImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/person")
public class PersonController {
    private final CrudService<Person, UUID> personService;
    private final PersonMapper personMapper;

    public PersonController(PersonServiceImpl personService, PersonMapper personMapper) {
        this.personService = personService;
        this.personMapper = personMapper;
    }

    @GetMapping("/")
    public @Valid List<PersonResponse> getPersons(@RequestParam(required = false, defaultValue = "0") @Min(0) @Max(1000) int page,
                                                  @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(1000) int size,
                                                  @RequestParam(required = false, defaultValue = "id") @NotBlank @Size(max = 50) String sortParam) {
        return personMapper.mapItemsToResponses(
                personService.getAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortParam)))
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
}

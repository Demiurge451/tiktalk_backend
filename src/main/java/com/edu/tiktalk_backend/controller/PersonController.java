package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.dto.request.PersonRequest;
import com.edu.tiktalk_backend.dto.response.PersonResponse;
import com.edu.tiktalk_backend.mapper.PersonMapper;
import com.edu.tiktalk_backend.model.Person;
import com.edu.tiktalk_backend.service.CrudService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    private final CrudService<Person, UUID> personService;
    private final PersonMapper personMapper;

    public PersonController(@Qualifier("personServiceImpl") CrudService<Person, UUID> personService, PersonMapper personMapper) {
        this.personService = personService;
        this.personMapper = personMapper;
    }

    @GetMapping("/")
    public List<PersonResponse> getPersons(@RequestParam(required = false, defaultValue = "0") int page,
                                           @RequestParam(required = false, defaultValue = "10") int size,
                                           @RequestParam(required = false, defaultValue = "id") String sortParam) {
        return personService.getListOfItems(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortParam)))
                .stream().map(personMapper::mapItemToResponse).toList();
    }

    @GetMapping("/{id}")
    public PersonResponse getPerson(@PathVariable UUID id) {
        return personMapper.mapItemToResponse(personService.getById(id));
    }

    @PostMapping("/")
    public void createPerson(@RequestBody PersonRequest personRequest) {
        personService.save(personMapper.mapRequestToItem(personRequest));
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable UUID id) {
        personService.delete(id);
    }

    @PutMapping("/{id}")
    public PersonResponse updatePerson(@PathVariable UUID id, @RequestBody PersonRequest personRequest) {
        return personMapper.mapItemToResponse(personService.update(id, personMapper.mapRequestToItem(personRequest)));
    }
}

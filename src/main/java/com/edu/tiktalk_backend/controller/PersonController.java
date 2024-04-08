package com.edu.tiktalk_backend.controller;

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

    public PersonController(@Qualifier("personServiceImpl") CrudService<Person, UUID> personService) {
        this.personService = personService;
    }

    @GetMapping("/")
    public List<Person> getPersons(@RequestParam(required = false, defaultValue = "0") int page,
                                   @RequestParam(required = false, defaultValue = "10") int size,
                                   @RequestParam(required = false, defaultValue = "id") String sortParam) {
        return personService.getListOfItems(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortParam)));
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable UUID id) {
        return personService.getById(id);
    }

    @PostMapping("/")
    public void createPerson(@RequestBody Person person) {
        personService.save(person);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable UUID id) {
        personService.delete(id);
    }

    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable UUID id, @RequestBody Person person) {
        return null;
    }
}

package com.edu.tiktalk_backend.controller;

import com.edu.tiktalk_backend.model.Person;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    @GetMapping("/")
    public List<Person> getPersons() {
        return null;
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable UUID id) {
        return null;
    }

    @PostMapping("/")
    public void createPerson(@RequestBody Person person) {

    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable UUID id) {

    }

    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable UUID id, @RequestBody Person person) {
        return null;
    }
}

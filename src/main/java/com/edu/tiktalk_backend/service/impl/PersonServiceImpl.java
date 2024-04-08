package com.edu.tiktalk_backend.service.impl;


import com.edu.tiktalk_backend.model.Person;
import com.edu.tiktalk_backend.repository.PersonRepository;
import com.edu.tiktalk_backend.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonServiceImpl implements CrudService<Person, UUID> {
    private final PersonRepository personRepository;


    @Override
    public List<Person> getListOfItems(PageRequest pageRequest) {
        return null;
    }

    @Override
    public Person getById(UUID uuid) {
        return null;
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {

    }

    @Override
    @Transactional
    public void save(Person item) {

    }

    @Override
    @Transactional
    public Person update(UUID uuid, Person item) {
        return null;
    }
}

package com.edu.tiktalk_backend.service.impl;


import com.edu.tiktalk_backend.exception.NotFoundException;
import com.edu.tiktalk_backend.mapper.PersonMapper;
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
    private final PersonMapper personMapper;

    @Override
    public List<Person> getListOfItems(PageRequest pageRequest) {
        return personRepository.findAll(pageRequest).getContent();
    }

    @Override
    public Person getById(UUID id) {
        return personRepository.findById(id).orElseThrow(() -> new NotFoundException("Person not found"));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        personRepository.delete(getById(id));
    }

    @Override
    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    @Override
    @Transactional
    public Person update(UUID id, Person item) {
        Person person = getById(id);
        personMapper.updatePerson(item, person);
        return personRepository.save(person);
    }
}

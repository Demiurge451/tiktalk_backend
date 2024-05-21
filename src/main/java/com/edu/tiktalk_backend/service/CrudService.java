package com.edu.tiktalk_backend.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface CrudService <T, ID> {
    List<T> getAll(PageRequest pageRequest);
    T getById(ID id);
    void delete(ID id);
    ID save(T item);
    T update(ID id, T item);
}
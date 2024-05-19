package com.edu.tiktalk_backend.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CrudService <T, ID> {
    List<T> getAll(PageRequest pageRequest);
    T getById(ID id);
    void delete(ID id);
    T save(T item);
    T update(ID id, T item);
}
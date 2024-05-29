package com.edu.tiktalk_backend.service;

import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CrudService<T, ID> {
    List<T> getAll(PageRequest pageRequest);

    T getById(ID id);

    void delete(ID loginId, ID itemId);

    ID save(ID loginId, T item);

    T update(ID loginId, ID itemId, T item);

    void checkBelong(ID loginId, ID itemId);
}

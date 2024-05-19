package com.edu.tiktalk_backend.mapper;

import com.edu.tiktalk_backend.model.id_container.IdContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IdMapper<T extends IdContainer<ID>, ID> {
    public List<ID> mapItemToId(List<T> podcasts) {
        if (podcasts == null || podcasts.isEmpty()) {
            return new ArrayList<>();
        }
        return podcasts.stream()
                .map(T::getId)
                .collect(Collectors.toList());
    }
}

package com.edu.tiktalk_backend.mapper;


import com.edu.tiktalk_backend.model.HasId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class IdMapper {
    public List<UUID> mapItemToId(List<HasId> podcasts) {
        if (podcasts == null || podcasts.isEmpty()) {
            return new ArrayList<>();
        }
        return podcasts.stream()
                .map(HasId::getId)
                .collect(Collectors.toList());
    }
}

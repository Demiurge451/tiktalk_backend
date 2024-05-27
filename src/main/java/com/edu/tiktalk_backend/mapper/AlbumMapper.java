package com.edu.tiktalk_backend.mapper;

import com.edu.tiktalk_backend.dto.request.AlbumRequest;
import com.edu.tiktalk_backend.dto.response.AlbumResponse;
import com.edu.tiktalk_backend.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class AlbumMapper {
    protected IdMapper idMapper;

    @Autowired
    protected void setAlbumMapper(IdMapper idMapper) {
        this.idMapper = idMapper;
    }
    public abstract Album mapRequestToItem(AlbumRequest albumRequest);

    @Mapping(target = "podcasts", expression = "java(idMapper.mapItemToId(mapHasIds(album.getPodcasts())))")
    public abstract AlbumResponse mapItemToResponse(Album album);

    public abstract List<AlbumResponse> mapItemsToResponses(List<Album> albums);

    public abstract void updateAlbum(Album source, @MappingTarget Album target);
    public abstract List<HasId> mapHasIds(List<Podcast> albums);
}

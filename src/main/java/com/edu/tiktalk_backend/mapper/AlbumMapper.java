package com.edu.tiktalk_backend.mapper;

import com.edu.tiktalk_backend.dto.request.AlbumRequest;
import com.edu.tiktalk_backend.dto.response.AlbumResponse;
import com.edu.tiktalk_backend.model.Album;
import com.edu.tiktalk_backend.model.Person;
import com.edu.tiktalk_backend.model.Podcast;
import com.edu.tiktalk_backend.model.Report;
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
    protected IdMapper<Podcast, UUID> idMapper;

    @Autowired
    protected void setAlbumMapper(IdMapper<Podcast, UUID> idMapper) {
        this.idMapper = idMapper;
    }
    public abstract Album mapRequestToItem(AlbumRequest albumRequest);

    @Mapping(target = "podcasts", expression = "java(idMapper.mapItemToId(album.getPodcasts()))")
    public abstract AlbumResponse mapItemToResponse(Album album);

    public abstract List<AlbumResponse> mapItemsToResponses(List<Album> albums);

    public abstract void updateAlbum(Album source, @MappingTarget Album target);
}

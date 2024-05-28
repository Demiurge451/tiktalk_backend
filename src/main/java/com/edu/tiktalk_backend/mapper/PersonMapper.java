package com.edu.tiktalk_backend.mapper;

import com.edu.tiktalk_backend.dto.User;
import com.edu.tiktalk_backend.dto.request.PersonRequest;
import com.edu.tiktalk_backend.dto.response.PersonResponse;
import com.edu.tiktalk_backend.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PersonMapper {
    protected IdMapper idMapper;

    @Autowired
    protected void setPersonMapper(IdMapper idMapper
    ) {
        this.idMapper = idMapper;
    }

    public abstract Person mapRequestToItem(PersonRequest personRequest);

    @Mapping(target = "podcasts", expression = "java(idMapper.mapItemToId(mapHasIds4(person.getPodcasts())))")
    @Mapping(target = "subscriptions", expression = "java(idMapper.mapItemToId(mapHasIds2(person.getSubscriptions())))")
    @Mapping(target = "likedPodcasts", expression = "java(idMapper.mapItemToId(mapHasIds4(person.getLikedPodcasts())))")
    @Mapping(target = "reports", expression = "java(idMapper.mapItemToId(mapHasIds3(person.getReports())))")
    @Mapping(target = "albums", expression = "java(idMapper.mapItemToId(mapHasIds(person.getAlbums())))")
    public abstract PersonResponse mapItemToResponse(Person person);

    public abstract List<PersonResponse> mapItemsToResponses(List<Person> persons);

    public abstract void updatePerson(Person source, @MappingTarget Person target);

    @Mapping(source = "firstName", target = "name")
    public abstract Person mapUser(User user);

    public abstract List<HasId> mapHasIds(List<Album> albums);

    public abstract List<HasId> mapHasIds2(List<Person> albums);

    public abstract List<HasId> mapHasIds3(List<Report> albums);

    public abstract List<HasId> mapHasIds4(List<Podcast> albums);

}

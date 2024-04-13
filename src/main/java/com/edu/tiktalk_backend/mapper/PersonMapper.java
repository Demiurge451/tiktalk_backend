package com.edu.tiktalk_backend.mapper;

import com.edu.tiktalk_backend.dto.request.PersonRequest;
import com.edu.tiktalk_backend.dto.response.PersonResponse;
import com.edu.tiktalk_backend.model.Album;
import com.edu.tiktalk_backend.model.Person;
import com.edu.tiktalk_backend.model.Podcast;
import com.edu.tiktalk_backend.model.Report;
import com.edu.tiktalk_backend.service.CrudService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.UUID;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class PersonMapper {
    protected IdMapper<Report, UUID> idMapperReport;
    protected IdMapper<Podcast, UUID> idMapperPodcast;
    protected IdMapper<Person, UUID> idMapperPerson;

    @Autowired
    protected void setPersonMapper(IdMapper<Report, UUID> idMapperReport,
                                   IdMapper<Podcast, UUID> idMapperPodcast,
                                   IdMapper<Person, UUID> idMapperPerson
    ) {
        this.idMapperPodcast = idMapperPodcast;
        this.idMapperPerson = idMapperPerson;
        this.idMapperReport = idMapperReport;
    }
    public abstract Person mapRequestToItem(PersonRequest personRequest);

    @Mapping(target = "podcasts", expression = "java(idMapperPodcast.mapItemToId(person.getPodcasts()))")
    @Mapping(target = "subscribers", expression = "java(idMapperPerson.mapItemToId(person.getSubscribers()))")
    @Mapping(target = "likedPodcasts", expression = "java(idMapperPodcast.mapItemToId(person.getLikedPodcasts()))")
    @Mapping(target = "reports", expression = "java(idMapperReport.mapItemToId(person.getReports()))")
    public abstract PersonResponse mapItemToResponse(Person person);

    public abstract void updatePerson(Person source, @MappingTarget Person target);
}

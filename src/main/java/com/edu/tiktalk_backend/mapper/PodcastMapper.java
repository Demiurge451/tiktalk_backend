package com.edu.tiktalk_backend.mapper;

import com.edu.tiktalk_backend.dto.request.PodcastRequest;
import com.edu.tiktalk_backend.dto.response.PodcastResponse;
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
public abstract class PodcastMapper {
    protected IdMapper<Person, UUID> idMapperPerson;
    protected IdMapper<Report, UUID> idMapperReport;
    protected CrudService<Person, UUID> personService;
    protected CrudService<Album, UUID> albumService;

    @Autowired
    protected void setPodcastMapper(CrudService<Person, UUID> personService,
                                    CrudService<Album, UUID> albumService,
                                    IdMapper<Person, UUID> idMapperPerson,
                                    IdMapper<Report, UUID> idMapperReport
    ) {
        this.personService = personService;
        this.albumService = albumService;
        this.idMapperPerson = idMapperPerson;
        this.idMapperReport = idMapperReport;
    }


    @Mapping(target = "person", expression = "java(personService.getById(podcastRequest.getPersonId()))")
    @Mapping(target = "album", expression = "java(albumService.getById(podcastRequest.getAlbumId()))")
    public abstract Podcast mapRequestToItem(PodcastRequest podcastRequest);

    @Mapping(target = "likedPersons", expression = "java(idMapperPerson.mapItemToId(podcast.getLikedPersons()))")
    @Mapping(target = "reports", expression = "java(idMapperReport.mapItemToId(podcast.getReports()))")
    @Mapping(target = "personId", source = "podcast.person.id")
    @Mapping(target = "albumId", source = "podcast.album.id")
    public abstract PodcastResponse mapItemToResponse(Podcast podcast);

    public abstract void updatePodcast(Podcast source, @MappingTarget Podcast target);
}

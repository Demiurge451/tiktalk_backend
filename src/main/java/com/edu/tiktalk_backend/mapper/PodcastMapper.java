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

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class PodcastMapper {
    protected IdMapper<Person, UUID> idMapperPerson;
    protected IdMapper<Report, UUID> idMapperReport;

    @Autowired
    protected void setPodcastMapper(
                                    IdMapper<Person, UUID> idMapperPerson,
                                    IdMapper<Report, UUID> idMapperReport
    ) {
        this.idMapperPerson = idMapperPerson;
        this.idMapperReport = idMapperReport;
    }


    public abstract Podcast mapRequestToItem(PodcastRequest podcastRequest);

    @Mapping(target = "likedPersons", expression = "java(idMapperPerson.mapItemToId(podcast.getLikedPersons()))")
    @Mapping(target = "reports", expression = "java(idMapperReport.mapItemToId(podcast.getReports()))")
    @Mapping(target = "personId", source = "podcast.person.id")
    @Mapping(target = "albumId", source = "podcast.album.id")
    public abstract PodcastResponse mapItemToResponse(Podcast podcast);

    public abstract List<PodcastResponse> mapItemsToResponses(List<Podcast> podcasts);

    public abstract void updatePodcast(Podcast source, @MappingTarget Podcast target);
}

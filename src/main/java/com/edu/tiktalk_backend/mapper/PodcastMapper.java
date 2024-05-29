package com.edu.tiktalk_backend.mapper;

import com.edu.tiktalk_backend.dto.User;
import com.edu.tiktalk_backend.dto.request.PodcastRequest;
import com.edu.tiktalk_backend.dto.request.ReportedPodcastRequest;
import com.edu.tiktalk_backend.dto.response.PodcastResponse;
import com.edu.tiktalk_backend.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class PodcastMapper {
    protected IdMapper idMapper;

    @Autowired
    protected void setPodcastMapper(
                                    IdMapper idMapperPerson
    ) {
        this.idMapper = idMapperPerson;
    }


    public abstract Podcast mapRequestToItem(PodcastRequest podcastRequest);

    @Mapping(target = "likedPersons", expression = "java(idMapper.mapItemToId(mapHasIds2(podcast.getLikedPersons())))")
    @Mapping(target = "reports", expression = "java(idMapper.mapItemToId(mapHasIds3(podcast.getReports())))")
    public abstract PodcastResponse mapItemToResponse(Podcast podcast);

    public abstract List<PodcastResponse> mapItemsToResponses(List<Podcast> podcasts);

    public abstract void updatePodcast(Podcast source, @MappingTarget Podcast target);
    public abstract ReportedPodcastRequest mapPodcastToReported(Podcast podcast);
    public abstract Person mapUser(User user);
    public abstract List<HasId> mapHasIds2(List<Person> albums);
    public abstract List<HasId> mapHasIds3(List<Report> albums);

}

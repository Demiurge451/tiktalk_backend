package com.edu.tiktalk_backend.mapper;

import com.edu.tiktalk_backend.dto.request.PodcastRequest;
import com.edu.tiktalk_backend.dto.request.ReportedPodcastRequest;
import com.edu.tiktalk_backend.dto.response.PodcastResponse;
import com.edu.tiktalk_backend.dto.response.ReportedPodcastResponse;
import com.edu.tiktalk_backend.model.Person;
import com.edu.tiktalk_backend.model.Podcast;
import com.edu.tiktalk_backend.model.Report;
import com.edu.tiktalk_backend.model.ReportedPodcast;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class ReportedPodcastMapper {
    public abstract ReportedPodcast mapRequestToItem(ReportedPodcastRequest  reportedPodcastRequest);

    public abstract ReportedPodcastResponse mapItemToResponse(ReportedPodcast reportedPodcast);

    public abstract List<ReportedPodcastResponse> mapItemsToResponses(List<ReportedPodcast> reportedPodcasts);
}

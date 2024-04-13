package com.edu.tiktalk_backend.mapper;

import com.edu.tiktalk_backend.dto.request.ReportRequest;
import com.edu.tiktalk_backend.dto.response.ReportResponse;
import com.edu.tiktalk_backend.model.Podcast;
import com.edu.tiktalk_backend.model.Person;
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
public abstract class ReportMapper {
    protected CrudService<Person, UUID> personService;
    protected CrudService<Podcast, UUID> podcastService;

    @Autowired
    protected void setReportMapper(@Qualifier("personServiceImpl") CrudService<Person, UUID> personService,
                                    @Qualifier("podcastServiceImpl") CrudService<Podcast, UUID> podcastService) {
        this.personService = personService;
        this.podcastService = podcastService;
    }

    @Mapping(target = "person", expression = "java(personService.getById(reportRequest.getPersonId()))")
    @Mapping(target = "podcast", expression = "java(podcastService.getById(reportRequest.getPodcastId()))")
    public abstract Report mapRequestToItem(ReportRequest reportRequest);

    @Mapping(target = "personId", source = "report.person.id")
    @Mapping(target = "podcastId", source = "report.podcast.id")
    public abstract ReportResponse mapItemToResponse(Report report);

    public abstract void updateReport(Report source, @MappingTarget Report target);
}

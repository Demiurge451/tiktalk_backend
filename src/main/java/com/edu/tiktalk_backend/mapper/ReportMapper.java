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

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class ReportMapper {
    public abstract Report mapRequestToItem(ReportRequest reportRequest);

    public abstract ReportResponse mapItemToResponse(Report report);

    public abstract List<ReportResponse> mapItemsToResponses(List<Report> reports);
}

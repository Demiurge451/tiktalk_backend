package com.edu.tiktalk_backend.mapper;

import com.edu.tiktalk_backend.dto.request.ReportRequest;
import com.edu.tiktalk_backend.dto.response.ReportResponse;
import com.edu.tiktalk_backend.model.Report;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ReportMapper {
    public abstract Report mapRequestToItem(ReportRequest reportRequest);

    public abstract ReportResponse mapItemToResponse(Report report);

    public abstract List<ReportResponse> mapItemsToResponses(List<Report> reports);
}

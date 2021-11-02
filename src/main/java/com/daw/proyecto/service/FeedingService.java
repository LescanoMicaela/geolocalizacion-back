package com.daw.proyecto.service;

import com.daw.proyecto.model.dto.request.FeedingRequest;
import com.daw.proyecto.model.dto.response.FeedingResponse;

import java.util.List;

public interface FeedingService {

    List<FeedingResponse> getColonyFeeding(Long colonyId);

    FeedingResponse saveFeeding(Long colonyId, FeedingRequest feeding);
}

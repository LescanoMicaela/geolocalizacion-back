package com.daw.proyecto.service;

import com.daw.proyecto.model.dto.request.FeedingRequest;
import com.daw.proyecto.model.dto.response.FeedingResponse;

import java.util.List;

/**
 * The interface Feeding service.
 */
public interface FeedingService {

    /**
     * Gets colony feeding.
     *
     * @param colonyId the colony id
     * @return the colony feeding
     */
    List<FeedingResponse> getColonyFeeding(Long colonyId);

    /**
     * Save feeding feeding response.
     *
     * @param colonyId the colony id
     * @param feeding  the feeding
     * @return the feeding response
     */
    FeedingResponse saveFeeding(Long colonyId, FeedingRequest feeding);
}

package com.daw.proyecto.service;

import com.daw.proyecto.model.Colony;
import com.daw.proyecto.model.dto.request.ColonyRequest;
import com.daw.proyecto.model.dto.response.ColonyResponse;

import java.util.List;

/**
 * The interface Colony service.
 */
public interface ColonyService {

    /**
     * Gets colonies.
     *
     * @return the colonies
     */
    List<ColonyResponse> getColonies();

    /**
     * Gets colony.
     *
     * @param id the id
     * @return the colony
     */
    Colony getColony(Long id);

    /**
     * Gets colony by id.
     *
     * @param id the id
     * @return the colony by id
     */
    ColonyResponse getColonyById(Long id);

    /**
     * Gets colony.
     *
     * @param lng the lng
     * @param lat the lat
     * @return the colony
     */
    ColonyResponse getColony(double lng, double lat);

    /**
     * Save colony colony response.
     *
     * @param colony the colony
     * @return the colony response
     */
    ColonyResponse saveColony(ColonyRequest colony);

    /**
     * Delete colony.
     *
     * @param id the id
     */
    void deleteColony(Long id);

    /**
     * Update colony colony response.
     *
     * @param id     the id
     * @param colony the colony
     * @return the colony response
     */
    ColonyResponse updateColony(Long id, ColonyRequest colony);
}
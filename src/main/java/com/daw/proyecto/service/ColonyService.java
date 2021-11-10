package com.daw.proyecto.service;

import com.daw.proyecto.model.Colony;
import com.daw.proyecto.model.dto.request.ColonyRequest;
import com.daw.proyecto.model.dto.response.ColonyResponse;

import java.util.List;

public interface ColonyService {

    List<ColonyResponse> getColonies();

    Colony getColony(Long id);

    ColonyResponse getColonyById(Long id);

    ColonyResponse getColony(double lng, double lat);

    ColonyResponse saveColony(ColonyRequest colony);

    void deleteColony(Long id);

    ColonyResponse updateColony(Long id, ColonyRequest colony);
}
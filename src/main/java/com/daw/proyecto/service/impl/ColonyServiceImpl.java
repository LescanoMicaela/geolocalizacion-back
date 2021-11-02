package com.daw.proyecto.service.impl;

import com.daw.proyecto.exception.EntityNotSavedException;
import com.daw.proyecto.exception.ResourceNotFoundException;
import com.daw.proyecto.mapper.ColonyMapper;
import com.daw.proyecto.model.Colony;
import com.daw.proyecto.model.dto.request.ColonyRequest;
import com.daw.proyecto.model.dto.response.ColonyResponse;
import com.daw.proyecto.repository.ColonyRespository;
import com.daw.proyecto.service.ColonyService;
import com.daw.proyecto.service.GeolocationService;
import com.daw.proyecto.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio de Colonia
 * para recuperar, guardar
 * y modificar los datos de la entidad
 * Colonia
 */
@Service
@Slf4j
public class ColonyServiceImpl implements ColonyService {

    private final ColonyRespository repo;

    private final ColonyMapper mapper;


    private final GeolocationService geoService;


    public ColonyServiceImpl(ColonyRespository repo, ColonyMapper mapper,
                             GeolocationService geoService) {
        this.repo = repo;
        this.mapper = mapper;
        this.geoService = geoService;
    }


    @Override
    public List<ColonyResponse> getColonies() {
        return repo.findAll()
                .stream().map(mapper::entityToDto)
                .collect(Collectors.toList());
    }


    public Colony getColony(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.COLONY_NOT_FOUND));
    }

    @Override
    public ColonyResponse getColonyById(Long id) {
        return mapper.entityToDto(getColony(id));
    }

    @Override
    public ColonyResponse getColony(double lat, double lng) {
        return geoService.getLocation(lat, lng)
                .map(repo::findByLocation)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.LOCATION_NOT_FOUND))
                .map(mapper::entityToDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.COLONY_NOT_FOUND));
    }

    @Override
    public ColonyResponse saveColony(ColonyRequest colony) {
        var geo = Optional.ofNullable(colony)
                .map(mapper::colonyToGeolocation)
                .map(geoService::saveGeolocation)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.COLONY_NOT_SAVED));

        return Optional.ofNullable(colony)
                .map(mapper::colonyToEntity)
                .map(repo::saveAndFlush)
                .map(mapper::entityToDto)
                .orElseThrow(() -> new EntityNotSavedException(Constants.COLONY_NOT_SAVED));
    }

    @Override
    public void deleteColony(Long id) {
        Optional.ofNullable(id)
                .map(repo::findById)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.COLONY_NOT_FOUND))
                .ifPresent(repo::delete);
    }

    @Override
    public ColonyResponse updateColony(ColonyResponse colony) {
        return repo.findById(Optional.ofNullable(colony.getId())
                        .orElseThrow(() -> new ResourceNotFoundException(Constants.COLONY_NOT_FOUND)))
                .map(repo::saveAndFlush)
                .map(mapper::entityToDto)
                .orElseThrow(() -> new EntityNotSavedException(Constants.COLONY_NOT_SAVED));
    }
}

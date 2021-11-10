package com.daw.proyecto.service.impl;

import com.daw.proyecto.exception.EntityNotSavedException;
import com.daw.proyecto.exception.ResourceNotFoundException;
import com.daw.proyecto.mapper.ColonyMapper;
import com.daw.proyecto.model.Colony;
import com.daw.proyecto.model.dto.request.ColonyRequest;
import com.daw.proyecto.model.dto.response.ColonyResponse;
import com.daw.proyecto.repository.ColonyRespository;
import com.daw.proyecto.repository.UserRepository;
import com.daw.proyecto.security.model.User;
import com.daw.proyecto.security.model.UserDetailsImpl;
import com.daw.proyecto.service.ColonyService;
import com.daw.proyecto.service.GeolocationService;
import com.daw.proyecto.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final UserRepository userRepo;

    private final GeolocationService geoService;


    public ColonyServiceImpl(ColonyRespository repo, ColonyMapper mapper,
                             GeolocationService geoService, UserRepository userRepo) {
        this.repo = repo;
        this.mapper = mapper;
        this.geoService = geoService;
        this.userRepo = userRepo;
    }


    @Override
    public List<ColonyResponse> getColonies() {
        return repo.findAll()
                .stream()
                .map(mapper::entityToDto)
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
                .map(c -> {
                    c.setCreateUser(getUser());
                    return repo.saveAndFlush(c);
                })
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
    public ColonyResponse updateColony(Long id, ColonyRequest colony) {
        return repo.findById(Optional.ofNullable(id)
                        .orElseThrow(() -> new ResourceNotFoundException(Constants.COLONY_NOT_FOUND)))
                .map(c -> {
                    c.setCats(colony.getCats());
                    return repo.saveAndFlush(c);
                })
                .map(mapper::entityToDto)
                .orElseThrow(() -> new EntityNotSavedException(Constants.COLONY_NOT_SAVED));
    }


    private User getUser() {
        var username = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(s -> s.getAuthentication())
                .map(s -> (UserDetailsImpl) s.getPrincipal())
                .map(s -> s.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException(Constants.NO_USER_LOGGED));
        return userRepo.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(Constants.NO_USER_FOUND));
    }
}

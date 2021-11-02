package com.daw.proyecto.service.impl;

import com.daw.proyecto.exception.EntityNotSavedException;
import com.daw.proyecto.exception.ResourceNotFoundException;
import com.daw.proyecto.mapper.GeolocationMapper;
import com.daw.proyecto.model.Geolocation;
import com.daw.proyecto.model.dto.response.GeolocationResponse;
import com.daw.proyecto.model.id.GeolocationId;
import com.daw.proyecto.repository.GeolocationRepository;
import com.daw.proyecto.service.GeolocationService;
import com.daw.proyecto.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Servicio Geolocalizacion
 * para recuperar y guardar los datos
 * de Geolocalicacion de la base de datos
 */
@Service
@Slf4j
public class GeoLocationServiceImpl implements GeolocationService {

    /**
     * GeolocalizacionRepository injection
     */
    private final GeolocationRepository repo;

    /**
     * GeolocalizacionMapper injection
     */
    private final GeolocationMapper mapper;

    /**
     * All args constructor
     *
     * @param repo geolocalizaicon repository
     */
    public GeoLocationServiceImpl(GeolocationRepository repo,
                                  GeolocationMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    /**
     * Este metodo guarda una geolocalizacion nueva
     * comprobando primero que esta no exista ya
     *
     * @param geo la geolocalizacion con latitud y longitud
     * @return Geolocalizacion guardada
     */
    @Override
    public Geolocation saveGeolocation(Geolocation geo) {

        Stream.of(repo.existsById(Optional.ofNullable(geo)
                        .orElseThrow(() -> new DataIntegrityViolationException(Constants.LOCATION_NOT_FOUND)).getId()))
                .filter(exist -> !exist)
                .findFirst()
                .orElseThrow(() -> new DataIntegrityViolationException(Constants.COLONY_ALREADY_EXISTS_IN_LOCATION));

        return Optional.ofNullable(repo.saveAndFlush(geo))
                .orElseThrow(() -> new EntityNotSavedException(Constants.LOCATION_NOT_SAVED));
    }

    /**
     * Este metodo recupera de base de datos
     * todas las geolocalizaziones guardadas
     *
     * @return GeolocalizacionDTO list de geolocalizacion
     */
    @Override
    public List<GeolocationResponse> getLocation() {
        return repo.findAll()
                .stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Geolocation> getLocation(double lat, double lng) {
        return repo.findById(GeolocationId
                .builder()
                .lat(Optional.ofNullable(lat)
                        .orElseThrow(() -> new ResourceNotFoundException(Constants.LAT_NOT_FOUND)))
                .lng((Optional.ofNullable(lng)
                        .orElseThrow(() -> new ResourceNotFoundException(Constants.LNG_NOT_FOUND))))
                .build());
    }
}

package com.daw.proyecto.service.impl;

import com.daw.proyecto.exception.EntityNotSavedException;
import com.daw.proyecto.exception.ResourceNotFoundException;
import com.daw.proyecto.mapper.GeolocalizacionMapper;
import com.daw.proyecto.model.Geolocalizacion;
import com.daw.proyecto.model.dto.response.GeolocalizacionDTO;
import com.daw.proyecto.model.id.GeolocalizacionId;
import com.daw.proyecto.repository.GeolocalizacionRepository;
import com.daw.proyecto.service.GeolocalizacionService;
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
public class GeolocalizacionServiceImpl implements GeolocalizacionService {

    /**
     * GeolocalizacionRepository injection
     */
    private final GeolocalizacionRepository repo;

    /**
     * GeolocalizacionMapper injection
     */
    private final GeolocalizacionMapper mapper;

    /**
     * All args constructor
     *
     * @param repo geolocalizaicon repository
     */
    public GeolocalizacionServiceImpl(GeolocalizacionRepository repo,
                                      GeolocalizacionMapper mapper) {
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
    public Geolocalizacion saveGeolocalizacion(Geolocalizacion geo) {

        Stream.of(repo.existsById(Optional.ofNullable(geo)
                        .orElseThrow(() -> new DataIntegrityViolationException("No se ha encontrado latitud ni lonfitud")).getId()))
                .filter(exist -> !exist)
                .findFirst()
                .orElseThrow(() -> new DataIntegrityViolationException("Ya existe una colonia registrada en esta geolocalizacion"));

        return Optional.ofNullable(repo.saveAndFlush(geo))
                .orElseThrow(() -> new EntityNotSavedException("No se ha podido guardar la geolocalizaicon"));
    }

    /**
     * Este metodo recupera de base de datos
     * todas las geolocalizaziones guardadas
     *
     * @return GeolocalizacionDTO list de geolocalizacion
     */
    @Override
    public List<GeolocalizacionDTO> getGeolocalizacion() {
        return repo.findAll()
                .stream()
                .map(mapper::entityToGeolocalizacionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Geolocalizacion> getLocalizacion(double latitud, double longitud) {
        return repo.findById(GeolocalizacionId
                        .builder()
                        .latitude(Optional.ofNullable(latitud)
                                .orElseThrow(() -> new ResourceNotFoundException("Latitud no encontrada")))
                        .longitud((Optional.ofNullable(longitud)
                                .orElseThrow(() -> new ResourceNotFoundException("Longituf no encontrada"))))
                        .build());
    }
}

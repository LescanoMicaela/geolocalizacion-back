package com.daw.proyecto.service.impl;

import com.daw.proyecto.exception.EntityNotSavedException;
import com.daw.proyecto.exception.ResourceNotFoundException;
import com.daw.proyecto.mapper.ColoniaMapper;
import com.daw.proyecto.model.Colonia;
import com.daw.proyecto.model.dto.request.ColoniaRequestDTO;
import com.daw.proyecto.model.dto.response.ColoniaDTO;
import com.daw.proyecto.repository.ColoniaRepository;
import com.daw.proyecto.service.ColoniaService;
import com.daw.proyecto.service.GeolocalizacionService;
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
public class ColoniaServiceImpl implements ColoniaService {

    private final ColoniaRepository repo;

    private final ColoniaMapper mapper;


    private final GeolocalizacionService geoService;


    public ColoniaServiceImpl(ColoniaRepository repo, ColoniaMapper mapper,
                              GeolocalizacionService geoService) {
        this.repo = repo;
        this.mapper = mapper;
        this.geoService = geoService;
    }


    @Override
    public List<ColoniaDTO> getColonias() {
        return repo.findAll()
                .stream().map(mapper::entityToColoniaDTO)
                .collect(Collectors.toList());
    }


    @Override
    public Colonia getColonia(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colonia no encontrada con id: " + id));
    }

    @Override
    public ColoniaDTO getColonia(double latitud, double longitud) {
        return geoService.getLocalizacion(latitud, longitud)
                .map(repo::findByLocalizacion)
                .orElseThrow(() -> new ResourceNotFoundException("Geolocalizacion no encontrada"))
                .map(mapper::entityToColoniaDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Colonia no encontrada para esa geolocalizacion"));
    }

    @Override
    public ColoniaDTO saveColonia(ColoniaRequestDTO colonia) {
        var geo = Optional.ofNullable(colonia)
                .map(mapper::coloniaToGeolocalizacion)
                .map(geoService::saveGeolocalizacion)
                .orElseThrow(() -> new ResourceNotFoundException("No se ha podido registrar la localización de la colonia"));

        return Optional.of(colonia)
                .map(mapper::coloniaDTOToEntity)
                .map(repo::saveAndFlush)
                .map(mapper::entityToColoniaDTO)
                .orElseThrow(() -> new EntityNotSavedException("No se pudo guardar la colonia"));
    }

    @Override
    public void deleteColonia(Long id) {
        Optional.of(id)
                .map(repo::findById)
                .orElseThrow(() -> new ResourceNotFoundException("Colonia no encontrada con ese id"))
                .ifPresent(repo::delete);
    }

    @Override
    public ColoniaDTO updateColonia(ColoniaDTO colonia) {
        return repo.findById(Optional.ofNullable(colonia.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el id de la colonia")))
                .map(repo::saveAndFlush)
                .map(mapper::entityToColoniaDTO)
                .orElseThrow(() -> new EntityNotSavedException("No se ha podido guardar los cambios en la colonia"));
    }
}

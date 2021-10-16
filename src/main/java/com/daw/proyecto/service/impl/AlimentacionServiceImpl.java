package com.daw.proyecto.service.impl;

import com.daw.proyecto.exception.EntityNotSavedException;
import com.daw.proyecto.mapper.AlimentacionMapper;
import com.daw.proyecto.model.dto.AlimentacionDTO;
import com.daw.proyecto.model.id.AlimentacionId;
import com.daw.proyecto.repository.AlimentacionRepository;
import com.daw.proyecto.service.AlimentacionService;
import com.daw.proyecto.service.ColoniaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Servicio de Alimentacion
 * para recuperar, guardar
 * y modificar los datos de la entidad
 * Alimentacion
 */
@Service
@Slf4j
public class AlimentacionServiceImpl implements AlimentacionService {

    private final AlimentacionRepository repo;

    private final ColoniaService coloniaService;

    private final AlimentacionMapper mapper;

    public AlimentacionServiceImpl(AlimentacionRepository repo, ColoniaService coloniaService, AlimentacionMapper mapper) {
        this.repo = repo;
        this.coloniaService = coloniaService;
        this.mapper = mapper;
    }

    @Override
    public List<AlimentacionDTO> getAlimentacionColonia(Long coloniaId) {
        var colonia = coloniaService.getColonia(coloniaId);

        return repo.findByIdColonia(colonia)
                .stream()
                .map(mapper::entityToAlimentacionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AlimentacionDTO saveAlimentacion(Long coloniaId, AlimentacionDTO alimentacionDTO) {
        var colonia = coloniaService.getColonia(coloniaId);

        return Stream.of(alimentacionDTO)
                .map(mapper::alimentacionDTOToEntity)
                .peek(a -> a.setId(AlimentacionId.builder()
                        .colonia(colonia)
                        .fecha(Instant.now())
                        .build()))
                .map(mapper::entityToAlimentacionDTO)
                .findFirst()
                .orElseThrow(() -> new EntityNotSavedException("No se ha podido guardar la alimentacion"));
    }

}

package com.daw.proyecto.mapper;

import com.daw.proyecto.exception.ResourceNotFoundException;
import com.daw.proyecto.model.Colonia;
import com.daw.proyecto.model.Geolocalizacion;
import com.daw.proyecto.model.dto.ColoniaDTO;
import com.daw.proyecto.model.id.GeolocalizacionId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ColoniaMapper {

    public ColoniaDTO entityToColoniaDTO(Colonia colonia) {
        return ColoniaDTO.builder()
                .id(colonia.getId())
                .latitud(Optional.ofNullable(colonia.getLocalizacion())
                        .orElseThrow(() -> new ResourceNotFoundException("Geolocalizacion no encontrada")).getId().getLatitude())
                .longitud(Optional.ofNullable(colonia.getLocalizacion())
                        .orElseThrow(() -> new ResourceNotFoundException("Geolocalizacion no encontrada")).getId().getLongitud())
                .numGatos(colonia.getNumGatos())
                .registro(Optional.ofNullable(colonia.isRegistro()).orElse(null))
                .build();
    }

    public Colonia coloniaDTOToEntity(ColoniaDTO colonia) {
        return Colonia.builder()
                .localizacion(Geolocalizacion
                        .builder()
                        .id(GeolocalizacionId.builder()
                                .latitude(colonia.getLatitud())
                                .longitud(colonia.getLongitud()).build())
                        .build())
                .numGatos(colonia.getNumGatos())
                .registro(Optional.ofNullable(colonia.isRegistro()).orElse(null))
                .build();

    }


    public Geolocalizacion coloniaToGeolocalizacion(ColoniaDTO colonia) {
        return Geolocalizacion.builder()
                .id(GeolocalizacionId.builder()
                        .longitud(Optional.ofNullable(colonia.getLongitud())
                                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la longitud")))
                        .latitude(Optional.ofNullable(colonia.getLatitud())
                                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la latitud")))
                        .build())
                .build();
    }
}

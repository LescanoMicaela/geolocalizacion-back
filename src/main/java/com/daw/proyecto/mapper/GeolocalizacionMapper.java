package com.daw.proyecto.mapper;

import com.daw.proyecto.exception.ResourceNotFoundException;
import com.daw.proyecto.model.Geolocalizacion;
import com.daw.proyecto.model.dto.response.GeolocalizacionDTO;
import com.daw.proyecto.model.id.GeolocalizacionId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GeolocalizacionMapper {

    public GeolocalizacionDTO entityToGeolocalizacionDTO(Geolocalizacion geo) {
        return GeolocalizacionDTO.builder()
                .latitud(Optional.ofNullable(geo.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado longitud ni latitud"))
                        .getLatitude())
                .longitud(geo.getId().getLongitud())
                .build();
    }

    public Geolocalizacion geolocalizacionDTOToEntity(GeolocalizacionDTO geo) {
        return Geolocalizacion.builder()
                .id(GeolocalizacionId
                        .builder()
                        .latitude(geo.getLatitud())
                        .longitud(geo.getLatitud())
                        .build())
                .build();

    }

}

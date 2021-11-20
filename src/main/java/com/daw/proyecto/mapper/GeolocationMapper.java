package com.daw.proyecto.mapper;

import com.daw.proyecto.exception.ResourceNotFoundException;
import com.daw.proyecto.model.Geolocation;
import com.daw.proyecto.model.dto.response.GeolocationResponse;
import com.daw.proyecto.utils.Constants;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GeolocationMapper {

    public GeolocationResponse entityToDto(Geolocation geo) {
        return GeolocationResponse.builder()
                .lat(Optional.ofNullable(geo.getId())
                        .orElseThrow(() -> new ResourceNotFoundException(Constants.LOCATION_NOT_FOUND))
                        .getLat())
                .lng(geo.getId().getLng())
                .build();
    }


}

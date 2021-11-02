package com.daw.proyecto.mapper;

import com.daw.proyecto.exception.ResourceNotFoundException;
import com.daw.proyecto.model.Colony;
import com.daw.proyecto.model.Geolocation;
import com.daw.proyecto.model.dto.request.ColonyRequest;
import com.daw.proyecto.model.dto.response.ColonyResponse;
import com.daw.proyecto.model.id.GeolocationId;
import com.daw.proyecto.util.Constants;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ColonyMapper {

    public ColonyResponse entityToDto(Colony colony) {
        return ColonyResponse.builder()
                .id(colony.getId())
                .lat(Optional.ofNullable(colony.getLocation())
                        .orElseThrow(() -> new ResourceNotFoundException(Constants.LOCATION_NOT_FOUND)).getId().getLat())
                .lng(Optional.ofNullable(colony.getLocation())
                        .orElseThrow(() -> new ResourceNotFoundException(Constants.LOCATION_NOT_FOUND)).getId().getLng())
                .cats(colony.getCats())
                .register(colony.isRegistration())
                .build();
    }

    public Colony colonyToEntity(ColonyRequest colony) {
        return Colony.builder()
                .location(Geolocation
                        .builder()
                        .id(GeolocationId.builder()
                                .lat(colony.getLat())
                                .lng(colony.getLng()).build())
                        .build())
                .cats(colony.getCats())
                .registration(colony.isRegister())
                .build();

    }


    public Geolocation colonyToGeolocation(ColonyRequest colony) {
        return Geolocation.builder()
                .id(GeolocationId.builder()
                        .lng(Optional.ofNullable(colony.getLng())
                                .orElseThrow(() -> new ResourceNotFoundException(Constants.LNG_NOT_FOUND)))
                        .lat(Optional.ofNullable(colony.getLat())
                                .orElseThrow(() -> new ResourceNotFoundException(Constants.LAT_NOT_FOUND)))
                        .build())
                .build();
    }
}

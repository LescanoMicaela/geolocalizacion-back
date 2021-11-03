package com.daw.proyecto.mapper;

import com.daw.proyecto.exception.ResourceNotFoundException;
import com.daw.proyecto.model.Colony;
import com.daw.proyecto.model.Feeding;
import com.daw.proyecto.model.Geolocation;
import com.daw.proyecto.model.dto.request.ColonyRequest;
import com.daw.proyecto.model.dto.response.ColonyResponse;
import com.daw.proyecto.model.id.GeolocationId;
import com.daw.proyecto.repository.FeedingRepository;
import com.daw.proyecto.util.Constants;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class ColonyMapper {

    private final FeedingRepository repo;

    public ColonyMapper(FeedingRepository repo) {
        this.repo = repo;
    }

    public ColonyResponse entityToDto(Colony colony) {
        var feed = repo.findFirstByIdColonyOrderByTimeDesc(colony).orElse(null);

        return ColonyResponse.builder()
                .id(colony.getId())
                .lat(Optional.ofNullable(colony.getLocation())
                        .orElseThrow(() -> new ResourceNotFoundException(Constants.LOCATION_NOT_FOUND)).getId().getLat())
                .lng(Optional.ofNullable(colony.getLocation())
                        .orElseThrow(() -> new ResourceNotFoundException(Constants.LOCATION_NOT_FOUND)).getId().getLng())
                .cats(colony.getCats())
                .register(colony.isRegistration())
                .food(Optional.ofNullable(feed).orElse(Feeding.builder().build()).isFood())
                .water(Optional.ofNullable(feed).orElse(Feeding.builder().build()).isWater())
                .time(Optional.ofNullable(feed).orElse(Feeding.builder().build()).getTime())
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
                .createDate(LocalDate.now())
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

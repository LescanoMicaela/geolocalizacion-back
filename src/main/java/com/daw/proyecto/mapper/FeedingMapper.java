package com.daw.proyecto.mapper;

import com.daw.proyecto.exception.ResourceNotFoundException;
import com.daw.proyecto.model.Feeding;
import com.daw.proyecto.model.dto.request.FeedingRequest;
import com.daw.proyecto.model.dto.response.FeedingResponse;
import com.daw.proyecto.util.Constants;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FeedingMapper {

    public FeedingResponse entityToDto(Feeding feeding) {
        return FeedingResponse.builder()
                .water(feeding.isWater())
                .food(feeding.isFood())
                .foodAvailable(feeding.isFoodAvailable())
                .waterAvailable(feeding.isWaterAvailable())
                .time(feeding.getId().getTime())
                .build();
    }

    public Feeding dtoToEntity(FeedingRequest feeding) {
        return Feeding.builder()
                .water(Optional.ofNullable(feeding).orElseThrow( () -> new ResourceNotFoundException(Constants.NULL_FEEDING))
                .isWater())
                .food(feeding.isFood())
                .foodAvailable(feeding.isWaterAvailable())
                .waterAvailable(feeding.isFoodAvailable())
                .build();
    }
}

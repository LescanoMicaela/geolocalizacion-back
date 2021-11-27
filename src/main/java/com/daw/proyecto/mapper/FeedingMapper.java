package com.daw.proyecto.mapper;

import com.daw.proyecto.exception.ResourceNotFoundException;
import com.daw.proyecto.model.Feeding;
import com.daw.proyecto.model.dto.request.FeedingRequest;
import com.daw.proyecto.model.dto.response.FeedingResponse;
import com.daw.proyecto.utils.Constants;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

/**
 * The type Feeding mapper.
 */
@Component
public class FeedingMapper {

    /**
     * Entity to dto feeding response.
     *
     * @param feeding the feeding
     * @return the feeding response
     */
    public FeedingResponse entityToDto(Feeding feeding) {
        return FeedingResponse.builder()
                .water(feeding.isWater())
                .food(feeding.isFood())
                .foodAvailable(feeding.isFoodAvailable())
                .waterAvailable(feeding.isWaterAvailable())
                .time(feeding.getId().getTime())
                .build();
    }

    /**
     * Dto to entity feeding.
     *
     * @param feeding the feeding
     * @return the feeding
     */
    public Feeding dtoToEntity(FeedingRequest feeding) {
        return Feeding.builder()
                .water(Optional.ofNullable(feeding).orElseThrow( () -> new ResourceNotFoundException(Constants.NULL_FEEDING))
                .isWater())
                .time(Instant.now())
                .food(feeding.isFood())
                .foodAvailable(feeding.isWaterAvailable())
                .waterAvailable(feeding.isFoodAvailable())
                .build();
    }
}

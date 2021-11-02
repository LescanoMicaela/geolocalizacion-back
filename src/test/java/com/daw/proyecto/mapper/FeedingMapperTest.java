package com.daw.proyecto.mapper;

import com.daw.proyecto.model.Colony;
import com.daw.proyecto.model.Feeding;
import com.daw.proyecto.model.dto.request.FeedingRequest;
import com.daw.proyecto.model.dto.response.FeedingResponse;
import com.daw.proyecto.model.id.FeedingId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class FeedingMapperTest {

    private FeedingMapper mapper;

    private FeedingResponse feedingResponse;
    private FeedingRequest feedingRequest;
    private Feeding feeding;

    @Before
    public void setUp() {
        mapper = new FeedingMapper();
        feedingResponse = FeedingResponse.builder()
                .time(Instant.now())
                .build();

        feedingRequest = FeedingRequest.builder()
                .build();

        feeding = Feeding.builder()
                .id(FeedingId.builder()
                        .time(Instant.now())
                        .colony(Colony.builder().build())
                        .build())
                .build();
    }

    @Test
    public void entityToDto() {
        var actual = mapper.entityToDto(feeding);
        assertNotNull(actual);
        assertEquals(feedingResponse, actual);
    }

    @Test
    public void dtoToEntity() {
        var actual = mapper.dtoToEntity(feedingRequest);
        assertNotNull(actual);
        assertEquals(feeding.isWater(), actual.isWater());
        assertEquals(feeding.isFoodAvailable(), actual.isFoodAvailable());
        assertEquals(feeding.isFood(), actual.isFood());
        assertEquals(feeding.isWaterAvailable(), actual.isWaterAvailable());

    }
}
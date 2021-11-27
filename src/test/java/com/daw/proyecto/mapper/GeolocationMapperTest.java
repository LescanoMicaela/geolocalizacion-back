package com.daw.proyecto.mapper;

import com.daw.proyecto.model.Geolocation;
import com.daw.proyecto.model.dto.response.GeolocationResponse;
import com.daw.proyecto.model.id.GeolocationId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class GeolocationMapperTest {

    private GeolocationMapper mapper;
    private Geolocation geo;

    private GeolocationResponse geolocationResponse;


    @Before
    public void setUp() {
        mapper = new GeolocationMapper();
        geo = Geolocation.builder()
                .id(GeolocationId.builder()
                        .lat(10.0)
                        .lng(10.0)
                        .build())
                .build();
        geolocationResponse = GeolocationResponse.builder()
                .lat(10.0)
                .lng(10.0)
                .build();

    }

    @Test
    public void entityToDto() {
        var actual = mapper.entityToDto(geo);
        assertNotNull(actual);

        assertEquals(geo.getId().getLat(), actual.getLat(), 0);
        assertEquals(geo.getId().getLat(), actual.getLng(), 0);

    }

}
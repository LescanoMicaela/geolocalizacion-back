package com.daw.proyecto.mapper;

import com.daw.proyecto.model.Colony;
import com.daw.proyecto.model.Geolocation;
import com.daw.proyecto.model.dto.request.ColonyRequest;
import com.daw.proyecto.model.dto.response.ColonyResponse;
import com.daw.proyecto.model.id.GeolocationId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ColonyMapperTest {

    private ColonyMapper mapper;

    private Colony colony;
    private ColonyResponse colonyResponse;
    private ColonyRequest request;
    private Geolocation geo;


    @Before
    public void setUp() {
        mapper = new ColonyMapper();

        colony = Colony.builder()
                .location(Geolocation.builder()
                        .id(GeolocationId.builder()
                                .lat(10.0)
                                .lng(10.0)
                                .build()).build())
                .cats(4)
                .id(1L)
                .registration(false)
                .build();

        colonyResponse = ColonyResponse.builder()
                .id(1L)
                .lng(10.0)
                .lat(10.0)
                .cats(4)
                .register(false)
                .build();

        request = ColonyRequest.builder()
                .lng(10.0)
                .lat(10.0)
                .cats(4)
                .register(false)
                .build();

        geo = Geolocation.builder()
                .id(GeolocationId.builder()
                        .lat(10.0)
                        .lng(10.0)
                        .build())
                .build();
    }

    @Test
    public void entityToDto() {
        var actual = mapper.entityToDto(colony);
        assertNotNull(actual);
        assertEquals(colonyResponse, actual);
    }

    @Test
    public void dtoToEntiy() {
        var actual = mapper.colonyToEntity(request);
        assertNotNull(actual);
        assertEquals(colony.isRegistration(), actual.isRegistration());
        assertEquals(colony.getCats(), actual.getCats());
        assertEquals(colony.getLocation().getId().getLat(), actual.getLocation().getId().getLat(), 0);

        assertEquals(colony.getLocation().getId().getLng(), actual.getLocation().getId().getLng(), 0);

    }

    @Test
    public void colonyToLocation() {
        var actual = mapper.colonyToGeolocation(request);
        assertNotNull(actual);
        assertEquals(geo.getId().getLat(), actual.getId().getLat(), 0);
        assertEquals(geo.getId().getLat(), actual.getId().getLat(), 0);

    }
}
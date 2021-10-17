package com.daw.proyecto.mapper;

import com.daw.proyecto.model.Geolocalizacion;
import com.daw.proyecto.model.dto.response.GeolocalizacionDTO;
import com.daw.proyecto.model.id.GeolocalizacionId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class GeolocalizacionMapperTest {

    private GeolocalizacionMapper mapper;
    private Geolocalizacion geo;

    private GeolocalizacionDTO geolocalizacionDTO;


    @Before
    public void setUp() {
        mapper = new GeolocalizacionMapper();
        geo = Geolocalizacion.builder()
                .id(GeolocalizacionId.builder()
                        .latitude(10.0)
                        .longitud(10.0)
                        .build())
                .build();
        geolocalizacionDTO = GeolocalizacionDTO.builder()
                .latitud(10.0)
                .longitud(10.0)
                .build();

    }

    @Test
    public void entityToGeolocalizacionDTO() {
        var actual = mapper.entityToGeolocalizacionDTO(geo);
        assertNotNull(actual);

        assertEquals(geo.getId().getLatitude(), actual.getLatitud(), 0);
        assertEquals(geo.getId().getLatitude(), actual.getLongitud(), 0);

    }

}
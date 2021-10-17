package com.daw.proyecto.mapper;

import com.daw.proyecto.model.Colonia;
import com.daw.proyecto.model.Geolocalizacion;
import com.daw.proyecto.model.dto.request.ColoniaRequestDTO;
import com.daw.proyecto.model.dto.response.ColoniaDTO;
import com.daw.proyecto.model.id.GeolocalizacionId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ColoniaMapperTest {

    private ColoniaMapper mapper;

    private Colonia colonia;
    private ColoniaDTO coloniaDTO;
    private ColoniaRequestDTO request;
    private Geolocalizacion geo;


    @Before
    public void setUp() {
        mapper = new ColoniaMapper();

        colonia = Colonia.builder()
                .localizacion(Geolocalizacion.builder()
                        .id(GeolocalizacionId.builder()
                                .latitude(10.0)
                                .longitud(10.0)
                                .build()).build())
                .numGatos(4)
                .id(1L)
                .registro(false)
                .build();

        coloniaDTO = ColoniaDTO.builder()
                .id(1L)
                .longitud(10.0)
                .latitud(10.0)
                .numGatos(4)
                .registro(false)
                .build();

        request = ColoniaRequestDTO.builder()
                .longitud(10.0)
                .latitud(10.0)
                .numGatos(4)
                .registro(false)
                .build();

        geo = Geolocalizacion.builder()
                .id(GeolocalizacionId.builder()
                        .latitude(10.0)
                        .longitud(10.0)
                        .build())
                .build();
    }

    @Test
    public void entityToColoniaDTO() {
        var actual = mapper.entityToColoniaDTO(colonia);
        assertNotNull(actual);
        assertEquals(coloniaDTO, actual);
    }

    @Test
    public void coloniaDTOToEntity() {
        var actual = mapper.coloniaDTOToEntity(request);
        assertNotNull(actual);
        assertEquals(colonia.isRegistro(), actual.isRegistro());
        assertEquals(colonia.getNumGatos(), actual.getNumGatos());
        assertEquals(colonia.getLocalizacion().getId().getLatitude(), actual.getLocalizacion().getId().getLatitude(), 0);

        assertEquals(colonia.getLocalizacion().getId().getLongitud(), actual.getLocalizacion().getId().getLongitud(), 0);

    }

    @Test
    public void coloniaToGeolocalizacion() {
        var actual = mapper.coloniaToGeolocalizacion(request);
        assertNotNull(actual);
        assertEquals(geo.getId().getLatitude(), actual.getId().getLatitude(), 0);
        assertEquals(geo.getId().getLatitude(), actual.getId().getLatitude(), 0);

    }
}
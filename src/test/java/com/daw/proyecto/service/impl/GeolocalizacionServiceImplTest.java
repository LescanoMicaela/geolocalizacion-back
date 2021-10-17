package com.daw.proyecto.service.impl;

import com.daw.proyecto.exception.EntityNotSavedException;
import com.daw.proyecto.mapper.GeolocalizacionMapper;
import com.daw.proyecto.model.Geolocalizacion;
import com.daw.proyecto.model.id.GeolocalizacionId;
import com.daw.proyecto.repository.GeolocalizacionRepository;
import com.daw.proyecto.service.GeolocalizacionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class GeolocalizacionServiceImplTest {


    private GeolocalizacionService service;

    @Mock
    private GeolocalizacionMapper mapper;

    @Mock
    private GeolocalizacionRepository repo;


    private Geolocalizacion geo;

    @Before
    public void setUp() {
        service = new GeolocalizacionServiceImpl(repo, mapper);
        geo = Geolocalizacion.builder()
                .id(GeolocalizacionId.builder()
                        .latitude(10.0)
                        .longitud(10.0)
                        .build())
                .build();
    }

    @Test
    public void saveGeolocalizacionOk() {
        when(repo.existsById(any())).thenReturn(false);
        when(repo.saveAndFlush(any())).thenReturn(geo);

        var actual = service.saveGeolocalizacion(geo);
        assertNotNull(actual);
        assertEquals(actual, geo);
        verify(repo, times(1)).existsById(any());
        verify(repo, times(1)).saveAndFlush(any());


    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveGeolocalizacionLanzaDataIntegrityException() {
        service.saveGeolocalizacion(null);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveGeolocalizacionLanzaDataIntegrityException2() {
        when(repo.existsById(any())).thenReturn(true);
        service.saveGeolocalizacion(geo);
        verify(repo, times(1)).existsById(any());
    }

    @Test(expected = EntityNotSavedException.class)
    public void saveGeolocalizacionLanzaEntityNotSavedException() {
        when(repo.existsById(any())).thenReturn(false);
        when(repo.saveAndFlush(any())).thenThrow(EntityNotSavedException.class);
        service.saveGeolocalizacion(geo);
        verify(repo, times(1)).existsById(any());
        verify(repo, times(1)).saveAndFlush(any());
    }


}
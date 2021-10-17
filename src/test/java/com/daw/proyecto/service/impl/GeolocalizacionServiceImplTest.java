package com.daw.proyecto.service.impl;

import com.daw.proyecto.exception.EntityNotSavedException;
import com.daw.proyecto.mapper.GeolocalizacionMapper;
import com.daw.proyecto.model.Geolocalizacion;
import com.daw.proyecto.model.dto.response.GeolocalizacionDTO;
import com.daw.proyecto.model.id.GeolocalizacionId;
import com.daw.proyecto.repository.GeolocalizacionRepository;
import com.daw.proyecto.service.GeolocalizacionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class GeolocalizacionServiceImplTest {


    private final List<GeolocalizacionDTO> lista = new ArrayList();
    private final List<Geolocalizacion> listaEntity = new ArrayList();
    private GeolocalizacionService service;
    @Mock
    private GeolocalizacionMapper mapper;
    @Mock
    private GeolocalizacionRepository repo;
    private Geolocalizacion geo;
    private GeolocalizacionDTO geolocalizacionDTO;

    @Before
    public void setUp() {
        service = new GeolocalizacionServiceImpl(repo, mapper);
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
        lista.add(geolocalizacionDTO);
        listaEntity.add(geo);
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
    }

    @Test
    public void getGeolocalizacionOk() {
        when(repo.findAll()).thenReturn(listaEntity);
        when(mapper.entityToGeolocalizacionDTO(any())).thenReturn(geolocalizacionDTO);

        var actual = service.getGeolocalizacion();
        assertNotNull(actual);
        assertEquals(actual, lista);
        verify(repo, times(1)).findAll();
        verify(mapper, times(1)).entityToGeolocalizacionDTO(any());

    }


    @Test
    public void getGeolocalizacionVacia() {
        when(repo.findAll()).thenReturn(Collections.emptyList());

        var actual = service.getGeolocalizacion();
        assertNotNull(actual);
        assertEquals(0,actual.size());
        verify(repo, times(1)).findAll();

    }

    @Test
    public void getLocalizacion() {
        when(repo.findById(any())).thenReturn(Optional.of(geo));
        var actual = service.getLocalizacion(10.0, 10.0);
        assertNotNull(actual);
        assertEquals(actual, Optional.of(geo));
        verify(repo, times(1)).findById(any());

    }


}
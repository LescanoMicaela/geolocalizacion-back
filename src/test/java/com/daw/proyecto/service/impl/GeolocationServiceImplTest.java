package com.daw.proyecto.service.impl;

import com.daw.proyecto.exception.EntityNotSavedException;
import com.daw.proyecto.mapper.GeolocationMapper;
import com.daw.proyecto.model.Geolocation;
import com.daw.proyecto.model.dto.response.GeolocationResponse;
import com.daw.proyecto.model.id.GeolocationId;
import com.daw.proyecto.repository.GeolocationRepository;
import com.daw.proyecto.service.GeolocationService;
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
public class GeolocationServiceImplTest {


    private final List<GeolocationResponse> lista = new ArrayList();
    private final List<Geolocation> listaEntity = new ArrayList();
    private GeolocationService service;
    @Mock
    private GeolocationMapper mapper;
    @Mock
    private GeolocationRepository repo;
    private Geolocation geo;
    private GeolocationResponse geolocationResponse;

    @Before
    public void setUp() {
        service = new GeoLocationServiceImpl(repo, mapper);
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
        lista.add(geolocationResponse);
        listaEntity.add(geo);
    }

    @Test
    public void saveGeolocation() {
        when(repo.existsById(any())).thenReturn(false);
        when(repo.saveAndFlush(any())).thenReturn(geo);

        var actual = service.saveGeolocation(geo);
        assertNotNull(actual);
        assertEquals(actual, geo);
        verify(repo, times(1)).existsById(any());
        verify(repo, times(1)).saveAndFlush(any());

    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveGeolocationDataIntegrityException() {
        service.saveGeolocation(null);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveGeolocationLanzaDataIntegrityException2() {
        when(repo.existsById(any())).thenReturn(true);
        service.saveGeolocation(geo);
        verify(repo, times(1)).existsById(any());
    }

    @Test(expected = EntityNotSavedException.class)
    public void saveGeolocationEntityNotSavedException() {
        when(repo.existsById(any())).thenReturn(false);
        when(repo.saveAndFlush(any())).thenThrow(EntityNotSavedException.class);
        service.saveGeolocation(geo);
    }

    @Test
    public void getGeolocation() {
        when(repo.findAll()).thenReturn(listaEntity);
        when(mapper.entityToDto(any())).thenReturn(geolocationResponse);

        var actual = service.getLocation();
        assertNotNull(actual);
        assertEquals(actual, lista);
        verify(repo, times(1)).findAll();
        verify(mapper, times(1)).entityToDto(any());

    }


    @Test
    public void getGeolocationEmpty() {
        when(repo.findAll()).thenReturn(Collections.emptyList());

        var actual = service.getLocation();
        assertNotNull(actual);
        assertEquals(0, actual.size());
        verify(repo, times(1)).findAll();

    }

    @Test
    public void getLocation() {
        when(repo.findById(any())).thenReturn(Optional.of(geo));
        var actual = service.getLocation(10.0, 10.0);
        assertNotNull(actual);
        assertEquals(actual, Optional.of(geo));
        verify(repo, times(1)).findById(any());

    }


}
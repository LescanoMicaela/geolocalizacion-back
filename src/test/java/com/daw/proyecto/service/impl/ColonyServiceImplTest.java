package com.daw.proyecto.service.impl;

import com.daw.proyecto.exception.EntityNotSavedException;
import com.daw.proyecto.exception.ResourceNotFoundException;
import com.daw.proyecto.mapper.ColonyMapper;
import com.daw.proyecto.model.Colony;
import com.daw.proyecto.model.Geolocation;
import com.daw.proyecto.model.dto.request.ColonyRequest;
import com.daw.proyecto.model.dto.response.ColonyResponse;
import com.daw.proyecto.model.id.GeolocationId;
import com.daw.proyecto.repository.ColonyRespository;
import com.daw.proyecto.repository.FeedingRepository;
import com.daw.proyecto.repository.UserRepository;
import com.daw.proyecto.service.ColonyService;
import com.daw.proyecto.service.GeolocationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ColonyServiceImplTest {


    private final List<Colony> colonies = new ArrayList<>();
    private final List<ColonyResponse> coloniesDTO = new ArrayList<>();
    private ColonyService service;
    @Mock
    private ColonyRespository repo;
    @Mock
    private UserRepository userRepo;
    @Mock
    private ColonyMapper mapper;
    @Mock
    private GeolocationService geoService;
    private Colony colony;
    private ColonyResponse colonyResponse;
    private ColonyRequest request;



    @Before
    public void setUp() {
        service = new ColonyServiceImpl(repo, mapper, geoService, userRepo);
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
        colonies.add(colony);
        coloniesDTO.add(colonyResponse);
    }

    @Test
    public void getColonies() {
        when(repo.findAll()).thenReturn(colonies);
        when(mapper.entityToDto(colony)).thenReturn(colonyResponse);
        var actual = service.getColonies();
        assertEquals(actual, coloniesDTO);

        verify(repo, times(1)).findAll();
        verify(mapper, times(1)).entityToDto(colony);
    }

    @Test
    public void getColoniesEmpty() {
        when(repo.findAll()).thenReturn(Collections.emptyList());
        var actual = service.getColonies();
        assertEquals(0, actual.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    public void getColony() {
        when(geoService.getLocation(10.0, 10.0)).thenReturn(Optional.of(Geolocation.builder().build()));
        when(repo.findByLocation(any())).thenReturn(Optional.of(colony));
        when(mapper.entityToDto(colony)).thenReturn(colonyResponse);

        var actual = service.getColony(10.0, 10.0);
        assertNotNull(actual);
        assertEquals(actual, colonyResponse);

        verify(geoService, times(1)).getLocation(10.0, 10.0);
        verify(repo, times(1)).findByLocation(any());
        verify(mapper, times(1)).entityToDto(colony);

    }

    @Test(expected = ResourceNotFoundException.class)
    public void getColonyResourNotFound() {
        when(geoService.getLocation(10.0, 10.0)).thenReturn(Optional.of(Geolocation.builder().build()));
        when(repo.findByLocation(any())).thenReturn(Optional.empty());
        service.getColony(10.0, 10.0);

    }

    @Test(expected = ResourceNotFoundException.class)
    public void getColonyThrowException() {
        when(geoService.getLocation(10.0, 10.0)).thenReturn(Optional.of(Geolocation.builder().build()));
        when(repo.findByLocation(any())).thenThrow(ResourceNotFoundException.class);
        service.getColony(10.0, 10.0);

    }

    @Test
    public void saveColony() {
        when(mapper.colonyToGeolocation(any())).thenReturn(Geolocation.builder().build());
        when(geoService.saveGeolocation(any())).thenReturn(Geolocation.builder().build());
        when(mapper.colonyToEntity(any())).thenReturn(colony);
        when(repo.saveAndFlush(any())).thenReturn(colony);
        when(mapper.entityToDto(any())).thenReturn(colonyResponse);

        var actual = service.saveColony(request);

        assertNotNull(actual);
        assertEquals(actual, colonyResponse);

        verify(mapper, times(1)).colonyToGeolocation(any());
        verify(geoService, times(1)).saveGeolocation(any());
        verify(mapper, times(1)).colonyToEntity(any());
        verify(repo, times(1)).saveAndFlush(any());
        verify(mapper, times(1)).entityToDto(any());

    }

    @Test(expected = ResourceNotFoundException.class)
    public void saveColonyResourceNotFoundException() {
        when(mapper.colonyToGeolocation(any())).thenReturn(null);
        service.saveColony(request);
    }

    @Test(expected = EntityNotSavedException.class)
    public void saveColonyEntityNotSavedException() {
        when(mapper.colonyToGeolocation(any())).thenReturn(Geolocation.builder().build());
        when(geoService.saveGeolocation(any())).thenReturn(Geolocation.builder().build());
        when(mapper.colonyToEntity(any())).thenReturn(colony);
        when(repo.saveAndFlush(any())).thenReturn(null);
        service.saveColony(request);
    }

    @Test
    public void deleteColony() {
        when(repo.findById(any())).thenReturn(Optional.of(colony));
        service.deleteColony(1L);

        verify(repo, times(1)).findById(any());

    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteColonyResourceNotFoundException() {
        service.deleteColony(null);
    }

//    @Test
//    public void updateColony() {
//        when(repo.findById(any())).thenReturn(Optional.of(colony));
//        when(repo.saveAndFlush(any())).thenReturn(colony);
//        when(mapper.entityToDto(any())).thenReturn(colonyResponse);
//        var actual = service.updateColony(colonyResponse);
//        assertNotNull(actual);
//        assertEquals(actual, colonyResponse);
//
//        verify(repo, times(1)).findById(any());
//        verify(repo, times(1)).saveAndFlush(any());
//        verify(mapper, times(1)).entityToDto(any());
//
//
//    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateColonyResourceNotFoundException() {
        when(repo.findById(any())).thenReturn(null);
        service.deleteColony(1L);
    }

//    @Test(expected = EntityNotSavedException.class)
//    public void updateColoniaLanzaEntityNotSavedException() {
//        when(repo.findById(any())).thenReturn(Optional.empty());
//        when(repo.saveAndFlush(any())).thenReturn(null);
//        when(mapper.entityToColoniaDTO(null)).thenThrow(EntityNotSavedException.class);
//
//        service.deleteColonia(1L);
//    }
}
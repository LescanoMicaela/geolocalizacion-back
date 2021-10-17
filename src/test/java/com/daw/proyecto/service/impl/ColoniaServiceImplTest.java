package com.daw.proyecto.service.impl;

import com.daw.proyecto.exception.EntityNotSavedException;
import com.daw.proyecto.exception.ResourceNotFoundException;
import com.daw.proyecto.mapper.ColoniaMapper;
import com.daw.proyecto.model.Colonia;
import com.daw.proyecto.model.Geolocalizacion;
import com.daw.proyecto.model.dto.request.ColoniaRequestDTO;
import com.daw.proyecto.model.dto.response.ColoniaDTO;
import com.daw.proyecto.model.id.GeolocalizacionId;
import com.daw.proyecto.repository.ColoniaRepository;
import com.daw.proyecto.service.ColoniaService;
import com.daw.proyecto.service.GeolocalizacionService;
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
public class ColoniaServiceImplTest {


    private final List<Colonia> colonias = new ArrayList<>();
    private final List<ColoniaDTO> coloniasDTO = new ArrayList<>();
    private ColoniaService service;
    @Mock
    private ColoniaRepository repo;
    @Mock
    private ColoniaMapper mapper;
    @Mock
    private GeolocalizacionService geoService;
    private Colonia colonia;
    private ColoniaDTO coloniaDTO;
    private ColoniaRequestDTO request;


    @Before
    public void setUp() {
        service = new ColoniaServiceImpl(repo, mapper, geoService);
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
        colonias.add(colonia);
        coloniasDTO.add(coloniaDTO);
    }

    @Test
    public void getColoniasOk() {
        when(repo.findAll()).thenReturn(colonias);
        when(mapper.entityToColoniaDTO(colonia)).thenReturn(coloniaDTO);
        var actual = service.getColonias();
        assertEquals(actual, coloniasDTO);

        verify(repo, times(1)).findAll();
        verify(mapper, times(1)).entityToColoniaDTO(colonia);
    }

    @Test
    public void getColoniasVacia() {
        when(repo.findAll()).thenReturn(Collections.emptyList());
        var actual = service.getColonias();
        assertEquals(0,actual.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    public void getColoniaOk() {
        when(geoService.getLocalizacion(10.0, 10.0)).thenReturn(Optional.of(Geolocalizacion.builder().build()));
        when(repo.findByLocalizacion(any())).thenReturn(Optional.of(colonia));
        when(mapper.entityToColoniaDTO(colonia)).thenReturn(coloniaDTO);

        var actual = service.getColonia(10.0, 10.0);
        assertNotNull(actual);
        assertEquals(actual, coloniaDTO);

        verify(geoService, times(1)).getLocalizacion(10.0, 10.0);
        verify(repo, times(1)).findByLocalizacion(any());
        verify(mapper, times(1)).entityToColoniaDTO(colonia);

    }

    @Test(expected = ResourceNotFoundException.class)
    public void getColoniaLanzaResourceNotFoundException() {
        when(geoService.getLocalizacion(10.0, 10.0)).thenReturn(Optional.of(Geolocalizacion.builder().build()));
        when(repo.findByLocalizacion(any())).thenReturn(Optional.empty());
        service.getColonia(10.0, 10.0);

    }

    @Test(expected = ResourceNotFoundException.class)
    public void getColoniaLanzaResourceNotFoundException2() {
        when(geoService.getLocalizacion(10.0, 10.0)).thenReturn(Optional.of(Geolocalizacion.builder().build()));
        when(repo.findByLocalizacion(any())).thenThrow(ResourceNotFoundException.class);
        service.getColonia(10.0, 10.0);

    }

    @Test
    public void saveColoniaOk() {
        when(mapper.coloniaToGeolocalizacion(any())).thenReturn(Geolocalizacion.builder().build());
        when(geoService.saveGeolocalizacion(any())).thenReturn(Geolocalizacion.builder().build());
        when(mapper.coloniaDTOToEntity(any())).thenReturn(colonia);
        when(repo.saveAndFlush(any())).thenReturn(colonia);
        when(mapper.entityToColoniaDTO(any())).thenReturn(coloniaDTO);

        var actual = service.saveColonia(request);

        assertNotNull(actual);
        assertEquals(actual, coloniaDTO);

        verify(mapper, times(1)).coloniaToGeolocalizacion(any());
        verify(geoService, times(1)).saveGeolocalizacion(any());
        verify(mapper, times(1)).coloniaDTOToEntity(any());
        verify(repo, times(1)).saveAndFlush(any());
        verify(mapper, times(1)).entityToColoniaDTO(any());

    }

    @Test(expected = ResourceNotFoundException.class)
    public void saveColoniaLanzaResourceNotFoundException() {
        when(mapper.coloniaToGeolocalizacion(any())).thenReturn(null);
        service.saveColonia(request);
    }

    @Test(expected = EntityNotSavedException.class)
    public void saveColoniaLanzaEntityNotSavedException() {
        when(mapper.coloniaToGeolocalizacion(any())).thenReturn(Geolocalizacion.builder().build());
        when(geoService.saveGeolocalizacion(any())).thenReturn(Geolocalizacion.builder().build());
        when(mapper.coloniaDTOToEntity(any())).thenReturn(colonia);
        when(repo.saveAndFlush(any())).thenReturn(null);
        service.saveColonia(request);
    }

    @Test
    public void deleteColoniaOk() {
        when(repo.findById(any())).thenReturn(Optional.of(colonia));
        service.deleteColonia(1L);

        verify(repo, times(1)).findById(any());

    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteColoniaLanzaResourceNotFoundException() {
        service.deleteColonia(null);
    }

    @Test
    public void updateColoniaOk() {
        when(repo.findById(any())).thenReturn(Optional.of(colonia));
        when(repo.saveAndFlush(any())).thenReturn(colonia);
        when(mapper.entityToColoniaDTO(any())).thenReturn(coloniaDTO);
        var actual = service.updateColonia(coloniaDTO);
        assertNotNull(actual);
        assertEquals(actual, coloniaDTO);

        verify(repo, times(1)).findById(any());
        verify(repo, times(1)).saveAndFlush(any());
        verify(mapper, times(1)).entityToColoniaDTO(any());


    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateColoniaLanzaResourceNotFoundException() {
        when(repo.findById(any())).thenReturn(null);
        service.deleteColonia(1L);
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
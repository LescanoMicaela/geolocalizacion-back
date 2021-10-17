package com.daw.proyecto.service.impl;

import com.daw.proyecto.exception.ResourceNotFoundException;
import com.daw.proyecto.mapper.AlimentacionMapper;
import com.daw.proyecto.model.Alimentacion;
import com.daw.proyecto.model.Colonia;
import com.daw.proyecto.model.dto.request.AlimentacionRequestDTO;
import com.daw.proyecto.model.dto.response.AlimentacionDTO;
import com.daw.proyecto.model.id.AlimentacionId;
import com.daw.proyecto.repository.AlimentacionRepository;
import com.daw.proyecto.service.AlimentacionService;
import com.daw.proyecto.service.ColoniaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class AlimentacionServiceImplTest {


    private final List<AlimentacionDTO> alimentacionList = new ArrayList<>();
    private final List<Alimentacion> list = new ArrayList<>();
    private AlimentacionService service;
    @Mock
    private ColoniaService coloniaService;
    @Mock
    private AlimentacionMapper mapper;
    @Mock
    private AlimentacionRepository repo;
    private AlimentacionDTO alimentacionDTO;
    private AlimentacionRequestDTO alimentacionRequestDTO;
    private Alimentacion alimentacion;

    @Before
    public void setUp() {
        service = new AlimentacionServiceImpl(repo, coloniaService, mapper);
        alimentacionDTO = AlimentacionDTO.builder()
                .fecha(Instant.now())
                .build();

        alimentacionRequestDTO = AlimentacionRequestDTO.builder()
                .build();
        alimentacionList.add(alimentacionDTO);

        alimentacion = Alimentacion.builder()
                .id(AlimentacionId.builder()
                        .fecha(Instant.now())
                        .colonia(Colonia.builder().build())
                        .build())
                .build();
        list.add(alimentacion);
    }

    @Test
    public void getAlimentacionColoniaOk() {
        var expected = alimentacionList;
        when(repo.findByIdColonia(any())).thenReturn(list);
        when(mapper.entityToAlimentacionDTO(any())).thenReturn(alimentacionDTO);
        var actual = service.getAlimentacionColonia(1L);

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(repo, times(1)).findByIdColonia(any());
        verify(mapper, times(1)).entityToAlimentacionDTO(any());
    }

    @Test
    public void getAlimentacionColoniaListaVacía() {
        var expected = new ArrayList<AlimentacionDTO>();
        when(repo.findByIdColonia(any())).thenReturn(Collections.emptyList());
        var actual = service.getAlimentacionColonia(1L);

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(repo, times(1)).findByIdColonia(any());

    }

    @Test(expected = ResourceNotFoundException.class)
    public void getAlimentacionColoniaLanzaException() {
        when(coloniaService.getColonia(1L)).thenThrow(ResourceNotFoundException.class);
        var actual = service.getAlimentacionColonia(1L);
    }

    @Test
    public void saveAlimentacionOk() {
        when(coloniaService.getColonia(anyLong())).thenReturn(Colonia.builder().build());
        when(mapper.alimentacionDTOToEntity(any())).thenReturn(alimentacion);
        when(repo.saveAndFlush(any())).thenReturn(alimentacion);
        when(mapper.entityToAlimentacionDTO(alimentacion)).thenReturn(alimentacionDTO);

        var actual = service.saveAlimentacion(1L, alimentacionRequestDTO);

        assertNotNull(actual);
        assertEquals(actual, alimentacionDTO);
        verify(coloniaService, times(1)).getColonia(anyLong());
        verify(mapper, times(1)).alimentacionDTOToEntity(any());
        verify(repo, times(1)).saveAndFlush(any());
        verify(mapper, times(1)).entityToAlimentacionDTO(alimentacion);

    }

    @Test(expected = ResourceNotFoundException.class)
    public void saveAlimentacionNull() {
        when(coloniaService.getColonia(1L)).thenReturn(Colonia.builder().build());
        when(mapper.alimentacionDTOToEntity(null)).thenThrow(ResourceNotFoundException.class);
        var actual = service.saveAlimentacion(1L, null);
    }


}
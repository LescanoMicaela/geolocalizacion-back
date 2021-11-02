package com.daw.proyecto.service.impl;

import com.daw.proyecto.exception.ResourceNotFoundException;
import com.daw.proyecto.mapper.FeedingMapper;
import com.daw.proyecto.model.Feeding;
import com.daw.proyecto.model.Colony;
import com.daw.proyecto.model.dto.request.FeedingRequest;
import com.daw.proyecto.model.dto.response.FeedingResponse;
import com.daw.proyecto.model.id.FeedingId;
import com.daw.proyecto.repository.FeedingRepository;
import com.daw.proyecto.service.FeedingService;
import com.daw.proyecto.service.ColonyService;
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
public class FeedingServiceImplTest {


    private final List<FeedingResponse> alimentacionList = new ArrayList<>();
    private final List<Feeding> list = new ArrayList<>();
    private FeedingService service;
    @Mock
    private ColonyService colonyService;
    @Mock
    private FeedingMapper mapper;
    @Mock
    private FeedingRepository repo;
    private FeedingResponse feedingResponse;
    private FeedingRequest feedingRequest;
    private Feeding feeding;

    @Before
    public void setUp() {
        service = new FeedingServiceImpl(repo, colonyService, mapper);
        feedingResponse = FeedingResponse.builder()
                .time(Instant.now())
                .build();

        feedingRequest = FeedingRequest.builder()
                .build();
        alimentacionList.add(feedingResponse);

        feeding = Feeding.builder()
                .id(FeedingId.builder()
                        .time(Instant.now())
                        .colony(Colony.builder().build())
                        .build())
                .build();
        list.add(feeding);
    }

    @Test
    public void getAlimentacionColoniaOk() {
        var expected = alimentacionList;
        when(repo.findByIdColony(any())).thenReturn(list);
        when(mapper.entityToDto(any())).thenReturn(feedingResponse);
        var actual = service.getColonyFeeding(1L);

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(repo, times(1)).findByIdColony(any());
        verify(mapper, times(1)).entityToDto(any());
    }

    @Test
    public void getAlimentacionColoniaListaVacía() {
        var expected = new ArrayList<FeedingResponse>();
        when(repo.findByIdColony(any())).thenReturn(Collections.emptyList());
        var actual = service.getColonyFeeding(1L);

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(repo, times(1)).findByIdColony(any());

    }

    @Test(expected = ResourceNotFoundException.class)
    public void getAlimentacionColoniaLanzaException() {
        when(colonyService.getColony(1L)).thenThrow(ResourceNotFoundException.class);
        var actual = service.getColonyFeeding(1L);
    }

    @Test
    public void saveAlimentacionOk() {
        when(colonyService.getColony(anyLong())).thenReturn(Colony.builder().build());
        when(mapper.dtoToEntity(any())).thenReturn(feeding);
        when(repo.saveAndFlush(any())).thenReturn(feeding);
        when(mapper.entityToDto(feeding)).thenReturn(feedingResponse);

        var actual = service.saveFeeding(1L, feedingRequest);

        assertNotNull(actual);
        assertEquals(actual, feedingResponse);
        verify(colonyService, times(1)).getColony(anyLong());
        verify(mapper, times(1)).dtoToEntity(any());
        verify(repo, times(1)).saveAndFlush(any());
        verify(mapper, times(1)).entityToDto(feeding);

    }

    @Test(expected = ResourceNotFoundException.class)
    public void saveAlimentacionNull() {
        when(colonyService.getColony(1L)).thenReturn(Colony.builder().build());
        when(mapper.dtoToEntity(null)).thenThrow(ResourceNotFoundException.class);
        var actual = service.saveFeeding(1L, null);
    }


}
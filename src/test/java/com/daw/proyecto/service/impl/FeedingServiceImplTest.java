package com.daw.proyecto.service.impl;

import com.daw.proyecto.exception.ResourceNotFoundException;
import com.daw.proyecto.mapper.FeedingMapper;
import com.daw.proyecto.model.Feeding;
import com.daw.proyecto.model.Colony;
import com.daw.proyecto.model.dto.request.FeedingRequest;
import com.daw.proyecto.model.dto.response.FeedingResponse;
import com.daw.proyecto.model.id.FeedingId;
import com.daw.proyecto.repository.FeedingRepository;
import com.daw.proyecto.repository.UserRepository;
import com.daw.proyecto.security.enums.ERole;
import com.daw.proyecto.security.model.Role;
import com.daw.proyecto.security.model.User;
import com.daw.proyecto.security.model.UserDetailsImpl;
import com.daw.proyecto.service.FeedingService;
import com.daw.proyecto.service.ColonyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.util.*;

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
    @Mock
    private UserRepository userRepo;

    private FeedingResponse feedingResponse;
    private FeedingRequest feedingRequest;
    private Feeding feeding;

    @Before
    public void setUp() {
        service = new FeedingServiceImpl(repo, colonyService, mapper, userRepo);
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

        //Set security context
        var user = User.builder().username("username@username.com")
                .roles(Set.of(Role.builder().id(1).name(ERole.ROLE_USER).build()))
                .name("name")
                .password("pass")
                .id(1L).build();
        UserDetailsImpl applicationUser = UserDetailsImpl.build(user);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(applicationUser);


        when(userRepo.findByUsername(anyString())).thenReturn(Optional.of(user));

    }

    @Test
    public void getFeeding() {
        when(repo.findByIdColony(any())).thenReturn(list);
        when(mapper.entityToDto(any())).thenReturn(feedingResponse);
        var actual = service.getColonyFeeding(1L);

        assertNotNull(actual);
        assertEquals(alimentacionList, actual);
        verify(repo, times(1)).findByIdColony(any());
        verify(mapper, times(1)).entityToDto(any());
    }

    @Test
    public void getFeedingEmptyList() {
        var expected = new ArrayList<FeedingResponse>();
        when(repo.findByIdColony(any())).thenReturn(Collections.emptyList());
        var actual = service.getColonyFeeding(1L);

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(repo, times(1)).findByIdColony(any());

    }

    @Test(expected = ResourceNotFoundException.class)
    public void getFeedingThrowsException() {
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
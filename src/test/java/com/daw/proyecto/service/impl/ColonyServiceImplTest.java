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
import com.daw.proyecto.security.enums.ERole;
import com.daw.proyecto.security.model.Role;
import com.daw.proyecto.security.model.User;
import com.daw.proyecto.security.model.UserDetailsImpl;
import com.daw.proyecto.service.ColonyService;
import com.daw.proyecto.service.GeolocationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

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


    @Test
    public void getColonyById() {
        when(repo.findById(any())).thenReturn(Optional.of(colony));
        when(mapper.entityToDto(colony)).thenReturn(colonyResponse);
        var actual = service.getColonyById(1L);
        assertNotNull(actual);
        assertEquals(actual, colonyResponse);
        verify(repo, times(1)).findById(any());
        verify(mapper, times(1)).entityToDto(colony);

    }

    @Test(expected = ResourceNotFoundException.class)
    public void getColonyThrowsResourceNotFound() {
        when(geoService.getLocation(10.0, 10.0)).thenReturn(Optional.of(Geolocation.builder().build()));
        when(repo.findByLocation(any())).thenReturn(Optional.empty());
        service.getColony(10.0, 10.0);

    }

    @Test(expected = ResourceNotFoundException.class)
    public void getColonyThrowsResourceNotFoundException() {
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
    public void saveColonyThrowsResourceNotFoundException() {
        when(mapper.colonyToGeolocation(any())).thenReturn(null);
        service.saveColony(request);
    }

    @Test(expected = EntityNotSavedException.class)
    public void saveColonyThrowsEntityNotSavedException() {
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
    public void deleteColonyThrowsResourceNotFoundException() {
        service.deleteColony(null);
    }

    @Test
    public void updateColony() {
        when(repo.findById(any())).thenReturn(Optional.of(colony));
        when(repo.saveAndFlush(any())).thenReturn(colony);
        when(mapper.entityToDto(any())).thenReturn(colonyResponse);
        var actual = service.updateColony(1L, request);
        assertNotNull(actual);
        assertEquals(actual, colonyResponse);
        verify(repo, times(1)).findById(any());
        verify(repo, times(1)).saveAndFlush(any());
        verify(mapper, times(1)).entityToDto(any());


    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateColonyThrowsResourceNotFoundException() {
        when(repo.findById(any())).thenReturn(null);
        service.deleteColony(1L);
    }

    @Test(expected = EntityNotSavedException.class)
    public void updateThrowsEntityNotSavedException() {
        when(repo.findById(any())).thenReturn(Optional.empty());
        service.updateColony(1L, request);
    }
}
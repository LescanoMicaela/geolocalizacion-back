package com.daw.proyecto.security.service.impl;

import com.daw.proyecto.mapper.UserMapper;
import com.daw.proyecto.repository.UserRepository;
import com.daw.proyecto.security.enums.ERole;
import com.daw.proyecto.security.model.Role;
import com.daw.proyecto.security.model.User;
import com.daw.proyecto.security.model.UserDetailsImpl;
import com.daw.proyecto.security.model.dto.request.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    UserRepository repo;
    @Mock
    UserMapper mapper;
    @Mock
    UserRepository userRepository;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtUtils jwtUtil;

    UserServiceImpl service;

    User user;
    UserDTO userDTO;

    @Before
    public void setUp() {
        service = new UserServiceImpl(repo, mapper, userRepository, authenticationManager, jwtUtil);
        user = User.builder()
                .id(1L)
                .password("pass")
                .username("username")
                .roles(Set.of(Role.builder().name(ERole.ROLE_USER).id(1).build()))
                .build();
        userDTO = UserDTO.builder()
                .id(1L)
                .username("username")
                .password("pass")
                .build();


    }

    @Test
    public void register() {
        when(repo.existsByUsername(any())).thenReturn(false);
        when(mapper.userDtoToUser(any())).thenReturn(user);
        when(repo.saveAndFlush(any())).thenReturn(user);

        var response = service.register(userDTO);
        assertEquals("REGISTRATION_SUCCESSFUL", response.getMessage());

        verify(repo, times(1)).existsByUsername(any());
        verify(mapper, times(1)).userDtoToUser(any());
        verify(repo, times(1)).saveAndFlush(any());
    }

    @Test
    public void authenticate() {

        UserDetailsImpl applicationUser = UserDetailsImpl.build(user);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(applicationUser);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtUtil.generateJwtToken(any())).thenReturn("jwtToken");

        var result = service.authenticate(userDTO);
        assertNotNull(result);
    }

    @Test
    public void loadByUser() {

        when(userRepository.findByUsername(any())).thenReturn(java.util.Optional.ofNullable(user));
        var result = service.loadUserByUsername("username");
        assertEquals(result, UserDetailsImpl.build(user));
        verify(userRepository, times(1)).findByUsername(any());
    }


}

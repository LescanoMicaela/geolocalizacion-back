package com.daw.proyecto.security.controller.impl;

import com.daw.proyecto.exception.ControllerAdvisor;
import com.daw.proyecto.security.model.dto.request.UserDTO;
import com.daw.proyecto.security.model.dto.response.JwtResponse;
import com.daw.proyecto.security.model.dto.response.MessageResponse;
import com.daw.proyecto.security.service.UserService;
import com.daw.proyecto.utils.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerImplTest {

    UserControllerImpl controller;
    UserDTO userDTO;
    MessageResponse messageResponse;
    @Mock
    private UserService service;
    private JwtResponse jwtResponse;
    private MockMvc mvc;


    @Before
    public void setUp() {
        controller = new UserControllerImpl(service);
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerAdvisor())
                .build();
        userDTO = UserDTO.builder()
                .id(1L)
                .username("username")
                .password("pass")
                .registration(true)
                .name("name")
                .surname("surname")
                .build();
        messageResponse = new MessageResponse(Constants.REGISTRATION_SUCCESSFUL);
    }

    @Test
    public void signIn() {
        var jwt = new JwtResponse(
                "token", 1L, "username",
                List.of("USER"), "name");
        var expected = ResponseEntity.ok(jwt);
        when(service.authenticate(any())).thenReturn(jwt);

        var actual = controller.authenticateUser(userDTO);
        assertNotNull(actual);
        assertEquals(expected,actual);
    }

    @Test
    public void registerUser() {
        var expected = ResponseEntity.ok(messageResponse);
        when(service.register(any())).thenReturn(messageResponse);
        var actual = controller.registerUser(userDTO);
        assertNotNull(actual);
        assertEquals(expected,actual);
    }


}
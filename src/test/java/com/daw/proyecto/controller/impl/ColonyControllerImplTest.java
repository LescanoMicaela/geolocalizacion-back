package com.daw.proyecto.controller.impl;

import com.daw.proyecto.controller.ColonyController;
import com.daw.proyecto.exception.ControllerAdvisor;
import com.daw.proyecto.exception.ResourceNotFoundException;
import com.daw.proyecto.model.dto.request.ColonyRequest;
import com.daw.proyecto.model.dto.response.ColonyResponse;
import com.daw.proyecto.service.ColonyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ColonyControllerImplTest {

    private final List<ColonyResponse> colonies = new ArrayList<>();
    @Mock
    private ColonyService service;
    private ColonyController controller;
    private ColonyResponse colonyResponse;

    private ColonyRequest request;
    private MockMvc mvc;


    @Before
    public void setUp() {
        controller = new ColonyControllerImpl(service);
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerAdvisor())
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
        colonies.add(colonyResponse);
    }

    @Test
    public void getColonies() {
        var expected = ResponseEntity.ok(colonies);
        when(service.getColonies()).thenReturn(colonies);

        var actual = controller.getColonies();
        assertNotNull(actual);
        assertEquals(actual, expected);
    }

    @Test
    public void getColoniesEmpty() {
        var expected = ResponseEntity.ok(Collections.emptyList());
        when(service.getColonies()).thenReturn(Collections.emptyList());

        var actual = controller.getColonies();
        assertNotNull(actual);
        assertEquals(actual, expected);
    }

    @Test
    public void getColony() {
        var expected = ResponseEntity.ok(colonyResponse);
        when(service.getColony(10.0, 10.0)).thenReturn(colonyResponse);

        var actual = controller.getColony(10.0, 10.0);
        assertNotNull(actual);
        assertEquals(actual, expected);
    }

    @Test
    public void getColonies404() throws Exception {
        var expected = ResponseEntity.status(404).build();
        when(service.getColony(10.0, 10.0)).thenThrow(new ResourceNotFoundException("No se ha encontrado"));

        mvc.perform(get("/v1/colony?lat=10.0&lng=10.0"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void saveColonia() {
        var expected = ResponseEntity.ok(colonyResponse);
        when(service.saveColony(any())).thenReturn(colonyResponse);

        var actual = controller.saveColony(request);
        assertNotNull(actual);
        assertEquals(actual, expected);
    }

    @Test
    public void saveColoniaRespose404() throws Exception {
        when(service.saveColony(any())).thenThrow(new ResourceNotFoundException("No se ha encontrado"));

        var mapper = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        var requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request);

        mvc.perform(post("/v1/colonia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound());
    }

}
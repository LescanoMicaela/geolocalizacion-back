package com.daw.proyecto.controller.impl;

import com.daw.proyecto.controller.ColoniaController;
import com.daw.proyecto.exception.ControllerAdvisor;
import com.daw.proyecto.exception.ResourceNotFoundException;
import com.daw.proyecto.model.dto.request.ColoniaRequestDTO;
import com.daw.proyecto.model.dto.response.ColoniaDTO;
import com.daw.proyecto.service.ColoniaService;
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
public class ColoniaControllerImplTest {

    private final List<ColoniaDTO> colonias = new ArrayList<>();
    @Mock
    private ColoniaService service;
    private ColoniaController controller;
    private ColoniaDTO coloniaDTO;

    private ColoniaRequestDTO request;
    private MockMvc mvc;


    @Before
    public void setUp() {
        controller = new ColoniaControllerImpl(service);
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerAdvisor())
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
        colonias.add(coloniaDTO);
    }

    @Test
    public void getColonias() {
        var expected = ResponseEntity.ok(colonias);
        when(service.getColonias()).thenReturn(colonias);

        var actual = controller.getColonias();
        assertNotNull(actual);
        assertEquals(actual, expected);
    }

    @Test
    public void getColoniasVacia() {
        var expected = ResponseEntity.ok(Collections.emptyList());
        when(service.getColonias()).thenReturn(Collections.emptyList());

        var actual = controller.getColonias();
        assertNotNull(actual);
        assertEquals(actual, expected);
    }

    @Test
    public void getColonia() {
        var expected = ResponseEntity.ok(coloniaDTO);
        when(service.getColonia(10.0, 10.0)).thenReturn(coloniaDTO);

        var actual = controller.getColonia(10.0, 10.0);
        assertNotNull(actual);
        assertEquals(actual, expected);
    }

    @Test
    public void getColoniaResponde404() throws Exception {
        var expected = ResponseEntity.status(404).build();
        when(service.getColonia(10.0, 10.0)).thenThrow(new ResourceNotFoundException("No se ha encontrado"));

        mvc.perform(get("/v1/colonia?latitud=10.0&longitud=10.0"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void saveColonia() {
        var expected = ResponseEntity.ok(coloniaDTO);
        when(service.saveColonia(any())).thenReturn(coloniaDTO);

        var actual = controller.saveColonia(request);
        assertNotNull(actual);
        assertEquals(actual, expected);
    }

    @Test
    public void saveColoniaRespose404() throws Exception {
        when(service.saveColonia(any())).thenThrow(new ResourceNotFoundException("No se ha encontrado"));

        var mapper = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        var requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request);

        mvc.perform(post("/v1/colonia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound());
    }

}
package com.daw.proyecto.controller.impl;

import com.daw.proyecto.controller.AlimentacionController;
import com.daw.proyecto.exception.ControllerAdvisor;
import com.daw.proyecto.exception.EntityNotSavedException;
import com.daw.proyecto.model.dto.request.AlimentacionRequestDTO;
import com.daw.proyecto.model.dto.response.AlimentacionDTO;
import com.daw.proyecto.service.AlimentacionService;
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

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class AliemntacionControllerImplTest {

    private final List<AlimentacionDTO> alimentacionList = new ArrayList<>();
    @Mock
    private AlimentacionService service;
    private AlimentacionController controller;
    private MockMvc mvc;
    private AlimentacionDTO alimentacionDTO;
    private AlimentacionRequestDTO alimentacionRequestDTO;


    @Before
    public void setUp() {
        controller = new AliemntacionControllerImpl(service);
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerAdvisor())
                .build();
        alimentacionDTO = AlimentacionDTO.builder()
                .fecha(Instant.now())
                .build();

        alimentacionRequestDTO = AlimentacionRequestDTO.builder()
                .build();
        alimentacionList.add(alimentacionDTO);

    }

    @Test
    public void getAlimentacion() {
        var expected = ResponseEntity.ok(alimentacionList);
        when(service.getAlimentacionColonia(any())).thenReturn(alimentacionList);
        var actual = controller.getAlimentacion(1L);

        assertNotNull(actual);
        assertEquals(actual, expected);
    }

    @Test
    public void getAlimentacionListaVacia() {
        var expected = ResponseEntity.ok(Collections.emptyList());
        when(service.getAlimentacionColonia(any())).thenReturn(Collections.emptyList());
        var actual = controller.getAlimentacion(1L);

        assertNotNull(actual);
        assertEquals(actual, expected);
    }


    @Test
    public void alimentarColonia() {
        var expected = ResponseEntity.ok(alimentacionDTO);
        when(service.saveAlimentacion(any(), any())).thenReturn(alimentacionDTO);

        var actual = controller.alimentarColonia(alimentacionRequestDTO, 1L);
        assertNotNull(actual);
        assertEquals(actual, expected);

    }

    @Test
    public void alimentarColoniaResponde404() throws Exception {

        var mapper = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        var requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(alimentacionRequestDTO);

        mvc.perform(post("/colonia/1/alimentacion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound());
    }
}
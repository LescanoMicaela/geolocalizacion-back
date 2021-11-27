package com.daw.proyecto.controller.impl;

import com.daw.proyecto.controller.FeedingController;
import com.daw.proyecto.exception.ControllerAdvisor;
import com.daw.proyecto.model.dto.request.FeedingRequest;
import com.daw.proyecto.model.dto.response.FeedingResponse;
import com.daw.proyecto.service.FeedingService;
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
public class FeedingControllerImplTest {

    private final List<FeedingResponse> feedingList = new ArrayList<>();
    @Mock
    private FeedingService service;
    private FeedingController controller;
    private MockMvc mvc;
    private FeedingResponse feedingResponse;
    private FeedingRequest feedingRequest;


    @Before
    public void setUp() {
        controller = new FeedingControllerImpl(service);
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerAdvisor())
                .build();
        feedingResponse = FeedingResponse.builder()
                .time(Instant.now())
                .build();

        feedingRequest = FeedingRequest.builder()
                .build();
        feedingList.add(feedingResponse);

    }

    @Test
    public void getFeeding() {
        var expected = ResponseEntity.ok(feedingList);
        when(service.getColonyFeeding(any())).thenReturn(feedingList);
        var actual = controller.getFeeding(1L);

        assertNotNull(actual);
        assertEquals(actual, expected);
    }

    @Test
    public void getFeedingEmpty() {
        var expected = ResponseEntity.ok(Collections.emptyList());
        when(service.getColonyFeeding(any())).thenReturn(Collections.emptyList());
        var actual = controller.getFeeding(1L);

        assertNotNull(actual);
        assertEquals(actual, expected);
    }


    @Test
    public void feedColony() {
        var expected = ResponseEntity.ok(feedingResponse);
        when(service.saveFeeding(any(), any())).thenReturn(feedingResponse);

        var actual = controller.feedColony(feedingRequest, 1L);
        assertNotNull(actual);
        assertEquals(actual, expected);

    }

    @Test
    public void feedingColony404() throws Exception {

        var mapper = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        var requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(feedingRequest);

        mvc.perform(post("/colony/1/feeding")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound());
    }
}
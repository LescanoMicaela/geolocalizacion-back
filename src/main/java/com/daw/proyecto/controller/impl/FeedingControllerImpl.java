package com.daw.proyecto.controller.impl;

import com.daw.proyecto.controller.FeedingController;
import com.daw.proyecto.model.dto.request.FeedingRequest;
import com.daw.proyecto.model.dto.response.FeedingResponse;
import com.daw.proyecto.service.FeedingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
@Tag(name = "Feeding controller", description = "Saves and gets colonies feeding history")
public class FeedingControllerImpl implements FeedingController {

    private final FeedingService service;

    public FeedingControllerImpl(FeedingService service) {
        this.service = service;
    }


    @Override
    @GetMapping(value = "/colony/{colonyId}/feeding")
    public ResponseEntity<List<FeedingResponse>> getFeeding(@PathVariable("colonyId") Long colonyId) {

        return ResponseEntity.ok(service.getColonyFeeding(colonyId));
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/colony/{colonyId}/feeding")
    public ResponseEntity<FeedingResponse> feedColony(@RequestBody FeedingRequest feedingRequest,
                                                      @PathVariable("colonyId") Long colonyId) {
        return ResponseEntity.ok(service.saveFeeding(colonyId, feedingRequest));
    }


}

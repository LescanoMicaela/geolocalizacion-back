package com.daw.proyecto.controller.impl;

import com.daw.proyecto.controller.ColonyController;
import com.daw.proyecto.model.dto.request.ColonyRequest;
import com.daw.proyecto.model.dto.response.ColonyResponse;
import com.daw.proyecto.service.ColonyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Colony controller", description = "Saves and get colonies")
public class ColonyControllerImpl implements ColonyController {

    private final ColonyService service;

    public ColonyControllerImpl(ColonyService service) {
        this.service = service;
    }

    @Override
    @GetMapping(value = "/colonies")
    public ResponseEntity<List<ColonyResponse>> getColonies() {
        return ResponseEntity.ok(service.getColonies());
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/colony")
    public ResponseEntity<ColonyResponse> getColony(@RequestParam double lat , @RequestParam double lng) {
        return ResponseEntity.ok(service.getColony(lat , lng));
    }

    @Override
    @GetMapping(value = "/colony/{colonyId}")
    public ResponseEntity<ColonyResponse> getColony(@PathVariable("colonyId") Long colonyId) {
        return ResponseEntity.ok(service.getColonyById(colonyId));
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/colony")
    public ResponseEntity<ColonyResponse> saveColony(@RequestBody ColonyRequest colony) {
        return ResponseEntity.ok(service.saveColony(colony));
    }




}

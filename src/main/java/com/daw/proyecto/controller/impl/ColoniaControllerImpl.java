package com.daw.proyecto.controller.impl;

import com.daw.proyecto.controller.ColoniaController;
import com.daw.proyecto.model.dto.request.ColoniaRequestDTO;
import com.daw.proyecto.model.dto.response.ColoniaDTO;
import com.daw.proyecto.service.ColoniaService;
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
@Tag(name = "Colonia controller", description = "Controlador para crear y recuperar colonias")
public class ColoniaControllerImpl implements ColoniaController {

    private final ColoniaService service;

    public ColoniaControllerImpl(ColoniaService service) {
        this.service = service;
    }

    @Override
    @GetMapping(value = "/colonias")
    public ResponseEntity<List<ColoniaDTO>> getColonias() {
        return ResponseEntity.ok(service.getColonias());
    }

    @Override
    @GetMapping(value = "/colonia")
    public ResponseEntity<ColoniaDTO> getColonia(@RequestParam double latitud , @RequestParam double longitud) {
        return ResponseEntity.ok(service.getColonia(latitud , longitud));
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/colonia")
    public ResponseEntity<ColoniaDTO> saveColonia(@RequestBody ColoniaRequestDTO colonia) {
        return ResponseEntity.ok(service.saveColonia(colonia));
    }




}

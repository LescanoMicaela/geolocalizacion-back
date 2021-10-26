package com.daw.proyecto.controller.impl;

import com.daw.proyecto.controller.AlimentacionController;
import com.daw.proyecto.model.dto.request.AlimentacionRequestDTO;
import com.daw.proyecto.model.dto.response.AlimentacionDTO;
import com.daw.proyecto.service.AlimentacionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1")
@Tag(name = "Alimentacion controller", description = "Controlador para crear y recuperar alimentacion de colonias")
public class AliemntacionControllerImpl implements AlimentacionController {

    private final AlimentacionService service;

    public AliemntacionControllerImpl(AlimentacionService service) {
        this.service = service;
    }


    @Override
    @GetMapping(value = "/colonia/{coloniaId}/alimentacion")
    public ResponseEntity<List<AlimentacionDTO>> getAlimentacion(@PathVariable("coloniaId") Long coloniaId) {

        return ResponseEntity.ok(service.getAlimentacionColonia(coloniaId));
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/colonia/{coloniaId}/alimentacion")
    public ResponseEntity<AlimentacionDTO> alimentarColonia(@RequestBody AlimentacionRequestDTO alimentacionDTO,
                                                            @PathVariable("coloniaId") Long coloniaId) {
        return ResponseEntity.ok(service.saveAlimentacion(coloniaId, alimentacionDTO));
    }


}

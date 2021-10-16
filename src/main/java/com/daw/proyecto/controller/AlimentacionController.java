package com.daw.proyecto.controller;

import com.daw.proyecto.model.dto.AlimentacionDTO;
import com.daw.proyecto.model.dto.ColoniaDTO;
import com.daw.proyecto.model.dto.ErrorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface AlimentacionController {

    @Operation(summary = "Recupera el listado de de alimentacion de una colonia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha encontrado respuesta",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AlimentacionDTO.class)))}),
            @ApiResponse(responseCode = "404", description = "No encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Error de servidor",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<AlimentacionDTO>> getAlimentacion(@PathVariable("coloniaId") Long coloniaId);


    @Operation(summary = "Alimenta una colonia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha guardado la colonia",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ColoniaDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Sin autorización",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "403", description = "Forbiden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "404", description = "No encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "409", description = "Conflicto",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Error de servidor",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AlimentacionDTO> alimentarColonia(@RequestBody AlimentacionDTO alimentacionDTO,
                                                     @PathVariable("coloniaId") Long coloniaId);


}

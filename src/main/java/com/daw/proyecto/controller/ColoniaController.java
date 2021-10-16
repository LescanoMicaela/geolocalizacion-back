package com.daw.proyecto.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ColoniaController {

    @Operation(summary = "Recupera el listado de colonias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha encontrado respuesta",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ColoniaDTO.class)))}),
            @ApiResponse(responseCode = "404", description = "No encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Error de servidor",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ColoniaDTO>> getColonias();


    @Operation(summary = "Recupera una colonia por localización")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha encontrado respuesta",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ColoniaDTO.class))}),
            @ApiResponse(responseCode = "404", description = "No encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Error de servidor",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ColoniaDTO> getColonia(@Parameter(name = "latitud", required = true, example = "10.00", description = "Latitud de la localizacion")
                                          @RequestParam double latitud,
                                          @Parameter(name = "longitud", required = true, example = "10.00", description = "longitud de la localizacion")
                                          @RequestParam double longitud);


    @Operation(summary = "Guarda a una colonia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha guardado la colonia",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ColoniaDTO.class))}),
            @ApiResponse(responseCode = "404", description = "No encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Error de servidor",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ColoniaDTO> saveColonia(@RequestBody ColoniaDTO colonia);


}

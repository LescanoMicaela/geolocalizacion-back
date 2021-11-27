package com.daw.proyecto.controller;

import com.daw.proyecto.model.dto.request.ColonyRequest;
import com.daw.proyecto.model.dto.response.ColonyResponse;
import com.daw.proyecto.model.dto.response.ErrorDTO;
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


/**
 * The interface Colony controller.
 */
public interface ColonyController {

    /**
     * Gets colonies.
     *
     * @return the colonies
     */
    @Operation(summary = "Get a list of colonies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ColonyResponse.class)))}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ColonyResponse>> getColonies();


    /**
     * Gets colony.
     *
     * @param lat the lat
     * @param lng the lng
     * @return the colony
     */
    @Operation(summary = "Gets a colony by location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ColonyResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ColonyResponse> getColony(@Parameter(name = "lat", required = true, example = "10.00", description = "Location latitude")
                                          @RequestParam double lat,
                                             @Parameter(name = "lng", required = true, example = "10.00", description = "Location longitude")
                                          @RequestParam double lng);


    /**
     * Gets colony.
     *
     * @param colonyId the colony id
     * @return the colony
     */
    @Operation(summary = "Gets a colony by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ColonyResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ColonyResponse> getColony(@PathVariable("colonyId") Long colonyId);


    /**
     * Update colony response entity.
     *
     * @param colonyId the colony id
     * @param colony   the colony
     * @return the response entity
     */
    @Operation(summary = "Updates a colony")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Colony updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ColonyResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    @PutMapping(value = "/colony/{colonyId}")
    ResponseEntity<ColonyResponse> updateColony(@PathVariable("colonyId") Long colonyId,
                                                @RequestBody ColonyRequest colony);

    /**
     * Save colony response entity.
     *
     * @param colony the colony
     * @return the response entity
     */
    @Operation(summary = "Saves a colony")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Colony saved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ColonyResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ColonyResponse> saveColony(@RequestBody ColonyRequest colony);



}

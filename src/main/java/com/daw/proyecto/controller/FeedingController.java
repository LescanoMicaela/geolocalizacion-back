package com.daw.proyecto.controller;

import com.daw.proyecto.model.dto.request.FeedingRequest;
import com.daw.proyecto.model.dto.response.FeedingResponse;
import com.daw.proyecto.model.dto.response.ColonyResponse;
import com.daw.proyecto.model.dto.response.ErrorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * The interface Feeding controller.
 */
public interface FeedingController {

    /**
     * Gets feeding.
     *
     * @param colonyId the colony id
     * @return the feeding
     */
    @Operation(summary = "Gets a list of a colony feeding history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = FeedingResponse.class)))}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<FeedingResponse>> getFeeding(@PathVariable("colonyId") Long colonyId);


    /**
     * Feed colony response entity.
     *
     * @param feedingRequest the feeding request
     * @param colonyId       the colony id
     * @return the response entity
     */
    @Operation(summary = "Feeds a colony")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The colony was saved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ColonyResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "409", description = "Conflict",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<FeedingResponse> feedColony(@RequestBody FeedingRequest feedingRequest,
                                               @PathVariable("colonyId") Long colonyId);


}

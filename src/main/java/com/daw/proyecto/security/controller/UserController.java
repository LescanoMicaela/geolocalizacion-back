package com.daw.proyecto.security.controller;

import com.daw.proyecto.security.model.dto.request.UserDTO;
import com.daw.proyecto.model.dto.response.ErrorDTO;
import com.daw.proyecto.security.model.dto.response.JwtResponse;
import com.daw.proyecto.security.model.dto.response.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * The interface User controller.
 */
@Validated
public interface UserController {

    /**
     * Register user response entity.
     *
     * @param user the user
     * @return the response entity
     */
    @Operation(summary = "User registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody UserDTO user);


    /**
     * Authenticate user response entity.
     *
     * @param user the user
     * @return the response entity
     */
    @Operation(summary = "User authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody UserDTO user);

}

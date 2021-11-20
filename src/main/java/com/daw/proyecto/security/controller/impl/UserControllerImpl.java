package com.daw.proyecto.security.controller.impl;

import com.daw.proyecto.security.controller.UserController;
import com.daw.proyecto.security.model.dto.request.UserDTO;
import com.daw.proyecto.security.model.dto.response.JwtResponse;
import com.daw.proyecto.security.model.dto.response.MessageResponse;
import com.daw.proyecto.security.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/auth")
@Tag(name = "User controller", description = "Controller to register users")
public class UserControllerImpl implements UserController {

    private final UserService service;


    public UserControllerImpl(UserService service) {
        this.service = service;
    }


    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody UserDTO loginRequest) {
        return ResponseEntity.ok(service.authenticate(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody UserDTO signUpRequest) {
        return ResponseEntity.ok(service.register(signUpRequest));
    }
}



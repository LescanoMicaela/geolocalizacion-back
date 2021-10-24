package com.daw.proyecto.controller.impl;

import com.daw.proyecto.controller.UserController;
import com.daw.proyecto.model.dto.request.UserDTO;
import com.daw.proyecto.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:4200/")
@Tag(name = "User controller", description = "Controlador para registrar usuarios/as")
public class UserControllerImpl implements UserController {

    private final UserService service;

    public UserControllerImpl(UserService service) {
        this.service = service;
    }

    @Override
    @PostMapping(value = "/registro")
    public ResponseEntity<UserDTO> registerUser(UserDTO user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.register(user));
    }
}

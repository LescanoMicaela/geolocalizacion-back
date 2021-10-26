package com.daw.proyecto.security.service;

import com.daw.proyecto.security.model.dto.request.UserDTO;
import com.daw.proyecto.security.model.dto.response.JwtResponse;
import com.daw.proyecto.security.model.dto.response.MessageResponse;

public interface UserService {

    MessageResponse register(UserDTO user);

    JwtResponse authenticate(UserDTO user);
}
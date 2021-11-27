package com.daw.proyecto.security.service;

import com.daw.proyecto.security.model.dto.request.UserDTO;
import com.daw.proyecto.security.model.dto.response.JwtResponse;
import com.daw.proyecto.security.model.dto.response.MessageResponse;

/**
 * The interface User service.
 */
public interface UserService {

    /**
     * Register message response.
     *
     * @param user the user
     * @return the message response
     */
    MessageResponse register(UserDTO user);

    /**
     * Authenticate jwt response.
     *
     * @param user the user
     * @return the jwt response
     */
    JwtResponse authenticate(UserDTO user);
}
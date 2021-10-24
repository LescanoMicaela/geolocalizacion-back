package com.daw.proyecto.service;

import com.daw.proyecto.model.dto.request.UserDTO;

public interface UserService {

    UserDTO register(UserDTO user);

}
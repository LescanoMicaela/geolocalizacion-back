package com.daw.proyecto.service.impl;

import com.daw.proyecto.exception.EntityNotSavedException;
import com.daw.proyecto.exception.UserAlreadyExistsException;
import com.daw.proyecto.mapper.UserMapper;
import com.daw.proyecto.model.dto.request.UserDTO;
import com.daw.proyecto.repository.UserRepository;
import com.daw.proyecto.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final UserMapper mapper;

    public UserServiceImpl(UserRepository repo,
                           UserMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public UserDTO register(UserDTO user) {
        Optional.of(user)
                .filter(u -> !repo.existsByEmail(u.getEmail()))
                .stream().findFirst()
                .orElseThrow(() -> new UserAlreadyExistsException("Ya existe una persona registrada con este email"));

        return Optional.of(user)
                .map(mapper::userDtoToUser)
                .map(repo::saveAndFlush)
                .map(mapper::userToUserDTO)
                .orElseThrow(() -> new EntityNotSavedException("No se ha registrado la persona"));
    }


}

package com.daw.proyecto.security.service.impl;

import com.daw.proyecto.exception.EntityNotSavedException;
import com.daw.proyecto.exception.UserAlreadyExistsException;
import com.daw.proyecto.mapper.UserMapper;
import com.daw.proyecto.security.model.dto.request.UserDTO;
import com.daw.proyecto.security.model.dto.response.JwtResponse;
import com.daw.proyecto.security.model.dto.response.MessageResponse;
import com.daw.proyecto.repository.UserRepository;
import com.daw.proyecto.security.model.UserDetailsImpl;
import com.daw.proyecto.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository repo;
    private final UserMapper mapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository repo, UserMapper mapper, UserRepository userRepository,
                           AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.repo = repo;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public MessageResponse register(UserDTO user) {
        Optional.of(user)
                .filter(u -> !repo.existsByUsername(u.getUsername()))
                .stream().findFirst()
                .orElseThrow(() -> new UserAlreadyExistsException("Ya existe una persona registrada con este email"));

        return Optional.of(user)
                .map(mapper::userDtoToUser)
                .map(repo::saveAndFlush)
                .map(a -> new MessageResponse("User registered successfully!"))
                .orElseThrow(() -> new EntityNotSavedException("No se ha registrado la persona"));
    }

    @Override
    public JwtResponse authenticate(UserDTO user) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles, userDetails.getNombre());
    }


    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByUsername(email)
                .map(u -> UserDetailsImpl.build(u))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}

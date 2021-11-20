package com.daw.proyecto.mapper;

import com.daw.proyecto.repository.RoleRepository;
import com.daw.proyecto.security.enums.ERole;
import com.daw.proyecto.security.model.Role;
import com.daw.proyecto.security.model.User;
import com.daw.proyecto.security.model.dto.request.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository repo;


    public UserMapper(PasswordEncoder passwordEncoder, RoleRepository repo) {
        this.passwordEncoder = passwordEncoder;
        this.repo = repo;
    }

    public User userDtoToUser(UserDTO user) {
        Set<Role> roles = new HashSet<>();
        Role role = repo.saveAndFlush(Role.builder()
                .name(ERole.ROLE_USER).build());
        roles.add(role);
        return User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .surname2(user.getSurname2())
                .registration(user.isRegistration())
                .roles(roles)
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
    }


}

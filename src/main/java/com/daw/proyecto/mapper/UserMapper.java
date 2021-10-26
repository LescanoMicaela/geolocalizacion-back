package com.daw.proyecto.mapper;

import com.daw.proyecto.security.enums.ERole;
import com.daw.proyecto.security.model.Role;
import com.daw.proyecto.security.model.Usuario;
import com.daw.proyecto.security.model.dto.request.UserDTO;
import com.daw.proyecto.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository repo;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario userDtoToUser(UserDTO user) {
        Set<Role> roles = new HashSet<>();
        Role role = repo.saveAndFlush(Role.builder()
                .name(ERole.ROLE_USER).build());
        roles.add(role);
        return Usuario.builder()
                .username(user.getUsername())
                .nombre(user.getNombre())
                .apellido(user.getApellido())
                .apellido2(user.getApellido2())
                .acreditacion(user.isAcreditacion())
                .roles(roles)
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
    }

    public UserDTO userToUserDTO(Usuario usuario) {
        return UserDTO.builder()
                .username(usuario.getUsername())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .apellido2(usuario.getApellido2())
                .acreditacion(usuario.isAcreditacion())
                .build();
    }


}

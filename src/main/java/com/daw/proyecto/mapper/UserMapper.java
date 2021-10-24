package com.daw.proyecto.mapper;

import com.daw.proyecto.model.Usuario;
import com.daw.proyecto.model.dto.request.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario userDtoToUser(UserDTO user) {
        return Usuario.builder()
                .email(user.getEmail())
                .nombre(user.getNombre())
                .apellido(user.getApellido())
                .apellido2(user.getApellido2())
                .acreditacion(user.isAcreditacion())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
    }

    public UserDTO userToUserDTO(Usuario usuario) {
        return UserDTO.builder()
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .apellido2(usuario.getApellido2())
                .acreditacion(usuario.isAcreditacion())
                .build();
    }


}

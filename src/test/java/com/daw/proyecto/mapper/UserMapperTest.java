package com.daw.proyecto.mapper;

import com.daw.proyecto.repository.RoleRepository;
import com.daw.proyecto.security.enums.ERole;
import com.daw.proyecto.security.model.Role;
import com.daw.proyecto.security.model.User;
import com.daw.proyecto.security.model.dto.request.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {

    Role role;
    User user;
    UserDTO userdto;
    private UserMapper mapper;
    @Mock
    private RoleRepository repo;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        mapper = new UserMapper(passwordEncoder, repo);
        role = Role.builder()
                .name(ERole.ROLE_USER).build();
        userdto = UserDTO.builder()
                .id(1L)
                .username("username")
                .name("name")
                .surname("surname2")
                .registration(true)
                .password("pass")
                .build();
        user = User.builder()
                .id(1L)
                .username("username")
                .name("name")
                .surname("surname2")
                .registration(true)
                .password("pass")
                .roles(Set.of(role))
                .build();
    }

    @Test
    public void userDtoToUser() {
        when(repo.saveAndFlush(any())).thenReturn(role);
        when(passwordEncoder.encode(any())).thenReturn("endodedPass");
        var result = mapper.userDtoToUser(userdto);
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getId(), result.getId());
        verify(repo, times(1)).saveAndFlush(any());
        verify(passwordEncoder, times(1)).encode(any());

    }


}
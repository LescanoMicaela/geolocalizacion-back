package com.daw.proyecto.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The type User details.
 */
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final Long id;

    private final String username;

    private final String name;

    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * Instantiates a new User details.
     *
     * @param id          the id
     * @param username    the username
     * @param password    the password
     * @param authorities the authorities
     * @param name        the name
     */
    public UserDetailsImpl(Long id, String username, String password,
                           Collection<? extends GrantedAuthority> authorities,
                           String name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.name = name;
    }

    /**
     * Build user details.
     *
     * @param user the user
     * @return the user details
     */
    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities,
                user.getName());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }
}
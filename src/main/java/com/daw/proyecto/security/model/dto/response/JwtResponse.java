package com.daw.proyecto.security.model.dto.response;

import java.util.List;

/**
 * The type Jwt response.
 */
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private final List<String> roles;
    private  String name;

    /**
     * Instantiates a new Jwt response.
     *
     * @param accessToken the access token
     * @param id          the id
     * @param username    the username
     * @param roles       the roles
     * @param name        the name
     */
    public JwtResponse(String accessToken, Long id, String username, List<String> roles, String name) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.name = name;
        this.roles = roles;
    }

    /**
     * Gets access token.
     *
     * @return the access token
     */
    public String getAccessToken() {
        return token;
    }

    /**
     * Sets access token.
     *
     * @param accessToken the access token
     */
    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    /**
     * Gets token type.
     *
     * @return the token type
     */
    public String getTokenType() {
        return type;
    }

    /**
     * Sets token type.
     *
     * @param tokenType the token type
     */
    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }


    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets roles.
     *
     * @return the roles
     */
    public List<String> getRoles() {
        return roles;
    }


    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }
}
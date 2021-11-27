package com.daw.proyecto.exception;

/**
 * The type User already exists exception.
 */
public class UserAlreadyExistsException extends RuntimeException {
    /**
     * Instantiates a new User already exists exception.
     *
     * @param msg the msg
     */
    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}

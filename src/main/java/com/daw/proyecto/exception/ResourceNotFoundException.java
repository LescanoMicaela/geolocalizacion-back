package com.daw.proyecto.exception;

/**
 * The type Resource not found exception.
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Instantiates a new Resource not found exception.
     *
     * @param msg the msg
     */
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}

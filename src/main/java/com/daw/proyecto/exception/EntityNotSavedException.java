package com.daw.proyecto.exception;

/**
 * The type Entity not saved exception.
 */
public class EntityNotSavedException extends RuntimeException {
    /**
     * Instantiates a new Entity not saved exception.
     *
     * @param msg the msg
     */
    public EntityNotSavedException(String msg) {
        super(msg);
    }
}

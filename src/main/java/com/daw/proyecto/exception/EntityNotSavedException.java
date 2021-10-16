package com.daw.proyecto.exception;

public class EntityNotSavedException extends RuntimeException {
    public EntityNotSavedException(String msg) {
        super(msg);
    }
}

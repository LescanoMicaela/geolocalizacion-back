package com.daw.proyecto.exception;

/**
 * The type Token expired exception.
 */
public class TokenExpiredException extends RuntimeException {
    /**
     * Instantiates a new Token expired exception.
     *
     * @param msg the msg
     */
    public TokenExpiredException(String msg) {
        super(msg);
    }
}

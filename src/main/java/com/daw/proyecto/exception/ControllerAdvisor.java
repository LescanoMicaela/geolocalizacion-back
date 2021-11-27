package com.daw.proyecto.exception;

import com.daw.proyecto.model.dto.response.ErrorDTO;
import com.daw.proyecto.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;

/**
 * The type Controller advisor.
 */
@Slf4j
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    /**
     * The constant ERROR_HANDLED.
     */
    public static final String ERROR_HANDLED = "Error handled {}";
    /**
     * The constant WARNING.
     */
    public static final String WARNING = "WARNING";
    /**
     * The constant ERROR.
     */
    public static final String ERROR = "ERROR";

    /**
     * Handle resource not found exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.info(ERROR_HANDLED, ex.getMessage());

        return new ResponseEntity<>((ErrorDTO.builder()
                .code(404)
                .message(ex.getMessage())
                .level(WARNING))
                .build(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle token expired response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorDTO> handleTokenExpired(TokenExpiredException ex) {
        log.info(ERROR_HANDLED, ex.getMessage());

        return new ResponseEntity<>((ErrorDTO.builder()
                .code(401)
                .message(ex.getMessage())
                .level(ERROR))
                .build(), HttpStatus.UNAUTHORIZED);
    }


    /**
     * Handle dataintegrity exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler( DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> handleDataintegrityException(DataIntegrityViolationException ex) {
        log.info(ERROR_HANDLED, ex.getMessage());

        return new ResponseEntity<>((ErrorDTO.builder()
                .code(409)
                .message(ex.getMessage())
                .level(ERROR))
                .build(), HttpStatus.CONFLICT);
    }


    /**
     * Handle dataintegrity exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler( EntityExistsException.class)
    public ResponseEntity<ErrorDTO> handleDataintegrityException(EntityExistsException ex) {
        log.info(ERROR_HANDLED, ex.getMessage());

        return new ResponseEntity<>((ErrorDTO.builder()
                .code(409)
                .message(Constants.ALREADY_EXIST)
                .level(ERROR))
                .build(), HttpStatus.CONFLICT);
    }


    /**
     * Handle enitty not saved response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(EntityNotSavedException.class)
    public ResponseEntity<ErrorDTO> handleEnittyNotSaved(EntityNotSavedException ex) {
        log.info(ERROR_HANDLED, ex.getMessage());

        return new ResponseEntity<>((ErrorDTO.builder()
                .code(404)
                .message(ex.getMessage())
                .level(WARNING))
                .build(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle user already exists response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        log.info(ERROR_HANDLED, ex.getMessage());

        return new ResponseEntity<>((ErrorDTO.builder()
                .code(403)
                .message(ex.getMessage())
                .level(WARNING))
                .build(), HttpStatus.FORBIDDEN);
    }


}

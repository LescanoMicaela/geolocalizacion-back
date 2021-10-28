package com.daw.proyecto.exception;

import com.daw.proyecto.model.dto.response.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    public static final String ERROR_HANDLED = "Error handled {}";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.info(ERROR_HANDLED, ex.getMessage());

        return new ResponseEntity<>((ErrorDTO.builder()
                .codigo(404)
                .mensaje(ex.getMessage())
                .nivel("WARNING"))
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler( DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> handleDataintegrityException(    DataIntegrityViolationException ex) {
        log.info(ERROR_HANDLED, ex.getMessage());

        return new ResponseEntity<>((ErrorDTO.builder()
                .codigo(409)
                .mensaje(ex.getMessage())
                .nivel("ERROR"))
                .build(), HttpStatus.CONFLICT);
    }


    @ExceptionHandler(EntityNotSavedException.class)
    public ResponseEntity<ErrorDTO> handleEnittyNotSaved(EntityNotSavedException ex) {
        log.info(ERROR_HANDLED, ex.getMessage());

        return new ResponseEntity<>((ErrorDTO.builder()
                .codigo(404)
                .mensaje(ex.getMessage())
                .nivel("WARNING"))
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        log.info(ERROR_HANDLED, ex.getMessage());

        return new ResponseEntity<>((ErrorDTO.builder()
                .codigo(403)
                .mensaje(ex.getMessage())
                .nivel("WARNING"))
                .build(), HttpStatus.FORBIDDEN);
    }


}

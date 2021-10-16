package com.daw.proyecto.exception;

import com.daw.proyecto.model.dto.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerAdvisor {

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


    @ExceptionHandler(EntityNotSavedException.class)
    public ResponseEntity<ErrorDTO> handleEnittyNotSaved(EntityNotSavedException ex) {
        log.info(ERROR_HANDLED, ex.getMessage());

        return new ResponseEntity<>((ErrorDTO.builder()
                .codigo(404)
                .mensaje(ex.getMessage())
                .nivel("WARNING"))
                .build(), HttpStatus.NOT_FOUND);
    }
}

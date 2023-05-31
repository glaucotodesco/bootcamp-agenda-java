package com.abutua.agenda.resources.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFoundException(EntityNotFoundException exception, HttpServletRequest request){
        StandardError error = new StandardError();
        HttpStatus status = HttpStatus.NOT_FOUND;

        error.setError("Resource not found");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        error.setStatus(status.value());
        error.setTimeStamp(Instant.now());
         
        return ResponseEntity.status(status).body(error);
    }
}

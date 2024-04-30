package com.quocnguyen.studentmanagement.rest_controllers;

import com.quocnguyen.studentmanagement.entities.ErrorResponse;
import com.quocnguyen.studentmanagement.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.function.EntityResponse;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException e) {
        final ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND, e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}

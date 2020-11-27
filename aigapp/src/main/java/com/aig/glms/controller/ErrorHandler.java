package com.aig.glms.controller;

import javax.validation.ValidationException;

import org.omg.CORBA.portable.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.aig.object.ServiceResponse;

@RestControllerAdvice
public class ErrorHandler {
	
    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<ServiceResponse> handleValidationException(ValidationException e) {
    	ServiceResponse error = new ServiceResponse( e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<ServiceResponse> handleApplicationException(ApplicationException e) {
    	ServiceResponse error = new ServiceResponse( e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
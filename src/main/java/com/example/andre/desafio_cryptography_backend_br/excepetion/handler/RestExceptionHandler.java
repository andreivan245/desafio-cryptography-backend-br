package com.example.andre.desafio_cryptography_backend_br.excepetion.handler;

import com.example.andre.desafio_cryptography_backend_br.excepetion.UserHasNullRequiredAttributeException;
import com.example.andre.desafio_cryptography_backend_br.excepetion.UserIdNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(UserIdNotFoundException.class)
    ResponseEntity<ErrorMessage> userIdNotFoundException(UserIdNotFoundException exception, HttpServletRequest request){
        ErrorMessage error = new ErrorMessage();

        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError("Id not found");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UserHasNullRequiredAttributeException.class)
    ResponseEntity<ErrorMessage> userHasNullRequiredAttributeException(UserHasNullRequiredAttributeException exception, HttpServletRequest request){
        ErrorMessage error = new ErrorMessage();

        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        error.setError("Mandatory attribute is null");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);
    }
}

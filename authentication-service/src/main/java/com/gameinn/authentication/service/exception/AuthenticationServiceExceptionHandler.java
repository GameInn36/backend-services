package com.gameinn.authentication.service.exception;

import com.gameinn.authentication.service.models.ErrorResponseDTO;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class AuthenticationServiceExceptionHandler {

    @ExceptionHandler
    ResponseEntity<?> handle(FeignException exception){
        ErrorResponseDTO error = new ErrorResponseDTO(new Date(), exception.status(), exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.valueOf(exception.status()));
    }

    @ExceptionHandler
    ResponseEntity<?> handle(InvalidEmailPasswordException exception){
        ErrorResponseDTO error = new ErrorResponseDTO(new Date(), exception.getStatus(), exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.valueOf(exception.getStatus()));
    }
}

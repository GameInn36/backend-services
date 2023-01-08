package com.gameinn.user.service.exception;

import com.gameinn.user.service.dto.ErrorResponseDTO;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoWriteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class UserServiceExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handle(InvalidCredentialsException e){
        ErrorResponseDTO error = new ErrorResponseDTO(new Date(), e.getStatus(), e.getClass().getSimpleName(),e.getMessage());
        return new ResponseEntity<>(error,HttpStatus.valueOf(e.getStatus()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handle(DuplicateGameLogException e){
        ErrorResponseDTO error = new ErrorResponseDTO(new Date(), e.getStatus(), e.getClass().getSimpleName(),e.getMessage());
        return new ResponseEntity<>(error,HttpStatus.valueOf(e.getStatus()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handle(UserNotFoundException e){
        ErrorResponseDTO error = new ErrorResponseDTO(new Date(), e.getStatus(), e.getClass().getSimpleName(),e.getMessage());
        return new ResponseEntity<>(error,HttpStatus.valueOf(e.getStatus()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handle(MongoWriteException e){
        ErrorResponseDTO error = new ErrorResponseDTO(new Date(), HttpStatus.NOT_ACCEPTABLE.value(),e.getClass().getSimpleName(),e.getError().getMessage());
        return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handle(DuplicateKeyException e){
        ErrorResponseDTO error = new ErrorResponseDTO(new Date(), HttpStatus.NOT_ACCEPTABLE.value(), e.getClass().getSimpleName(),e.getErrorMessage());
        return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handle(Exception e){
        ErrorResponseDTO error = new ErrorResponseDTO(new Date(), HttpStatus.NOT_ACCEPTABLE.value(), e.getClass().getSimpleName(),e.getMessage());
        return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
    }
}

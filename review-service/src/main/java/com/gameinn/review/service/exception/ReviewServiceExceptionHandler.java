package com.gameinn.review.service.exception;

import com.gameinn.review.service.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ReviewServiceExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handleException(ReviewNotFoundException e)
    {
        return new ResponseEntity<>(new ErrorResponseDTO(new Date(), e.getStatus(), e.getClass().getSimpleName(), e.getMessage()), HttpStatus.valueOf(e.getStatus()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handleException(ReviewPageException e)
    {
        return new ResponseEntity<>(new ErrorResponseDTO(new Date(), e.getStatus(), e.getClass().getSimpleName(), e.getMessage()), HttpStatus.valueOf(e.getStatus()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handleException(ImproperReviewException e)
    {
        return new ResponseEntity<>(new ErrorResponseDTO(new Date(), e.getStatus(), e.getClass().getSimpleName(), e.getMessage()), HttpStatus.valueOf(e.getStatus()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handleException(Exception e)
    {
        return new ResponseEntity<>(new ErrorResponseDTO(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getClass().getSimpleName(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

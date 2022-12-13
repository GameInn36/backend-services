package com.gameinn.game.service;

import com.gameinn.game.service.dto.ErrorResponseDTO;
import com.gameinn.game.service.exception.GameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GameServiceExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handleException(GameNotFoundException e)
    {
        return new ResponseEntity<ErrorResponseDTO>(new ErrorResponseDTO(new Date(), HttpStatus.NOT_FOUND.value(), "Not found", e.getMessage(), "sallama_path"), HttpStatus.NOT_FOUND);
    }

}

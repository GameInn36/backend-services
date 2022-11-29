package com.gameinn.authentication.service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@Slf4j
public class JwtTokenIncorrectStructureException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public JwtTokenIncorrectStructureException(String msg){
        super(msg);
        log.error(this.getClass().getName() + ": " + msg);
    }
}

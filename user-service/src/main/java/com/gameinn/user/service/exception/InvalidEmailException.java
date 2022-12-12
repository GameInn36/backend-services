package com.gameinn.user.service.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidEmailException extends RuntimeException{
    public InvalidEmailException(String msg){
        super(msg);
        log.error(this.getClass().getName() + ": " + msg);
    }
}

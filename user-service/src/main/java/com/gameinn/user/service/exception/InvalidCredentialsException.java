package com.gameinn.user.service.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidCredentialsException extends RuntimeException{
    private final int status;
    public InvalidCredentialsException(String msg, int status){
        super(msg);
        this.status = status;
        log.error(this.getClass().getName() + ": " + msg);
    }

    public int getStatus(){
        return status;
    }
}

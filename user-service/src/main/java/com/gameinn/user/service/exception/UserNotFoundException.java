package com.gameinn.user.service.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserNotFoundException extends RuntimeException {
    private final int status;
    public UserNotFoundException(String msg, int status){
        super(msg);
        this.status = status;
        log.error(this.getClass().getName() + ": " + msg);
    }

    public int getStatus(){
        return status;
    }
}

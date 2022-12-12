package com.gameinn.authentication.service.exception;

public class InvalidUserNamePasswordException extends RuntimeException{
    private final int status;
    public InvalidUserNamePasswordException(String msg, int status){
        super(msg);
        this.status = status;
    }

    public int getStatus(){
        return this.status;
    }
}

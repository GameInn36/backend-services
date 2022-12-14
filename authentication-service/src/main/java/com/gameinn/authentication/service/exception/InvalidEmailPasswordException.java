package com.gameinn.authentication.service.exception;

public class InvalidEmailPasswordException extends RuntimeException{
    private final int status;
    public InvalidEmailPasswordException(String msg, int status){
        super(msg);
        this.status = status;
    }

    public int getStatus(){
        return this.status;
    }
}

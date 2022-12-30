package com.gameinn.user.service.exception;

public class DuplicateGameLogException extends RuntimeException{
    private final int status;

    public DuplicateGameLogException(String msg,int status){
        super(msg);
        this.status = status;
    }

    int getStatus(){
        return this.status;
    }
}

package com.gameinn.review.service.exception;

public class ReviewPageException extends Exception{
    private final int status;

    public ReviewPageException(String msg, int status){
        super(msg);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}

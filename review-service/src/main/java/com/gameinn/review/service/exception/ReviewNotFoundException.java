package com.gameinn.review.service.exception;

public class ReviewNotFoundException extends Exception{
    private final int status;
    public ReviewNotFoundException(String msg, int status)
    {
        super(msg);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}

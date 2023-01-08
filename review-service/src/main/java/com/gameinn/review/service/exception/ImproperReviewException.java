package com.gameinn.review.service.exception;

public class ImproperReviewException extends RuntimeException{
    private final int status;

    public ImproperReviewException(String msg, int status){
        super(msg);
        this.status = status;
    }

    public int getStatus(){
        return this.status;
    }
}

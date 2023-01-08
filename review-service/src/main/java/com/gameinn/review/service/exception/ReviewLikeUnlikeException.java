package com.gameinn.review.service.exception;

public class ReviewLikeUnlikeException extends RuntimeException{
    private final int status;

    public ReviewLikeUnlikeException(String msg, int status){
        super(msg);
        this.status = status;
    }

    public int getStatus(){
        return this.status;
    }
}

package com.gameinn.game.service.exception;

public class GameNotFoundException extends Exception {
    private final int status;
    public GameNotFoundException(String msg, int status)
    {
        super(msg);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}

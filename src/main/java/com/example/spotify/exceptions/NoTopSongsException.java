package com.example.spotify.exceptions;

public class NoTopSongsException extends Exception {

    /**
     * Invoked in the event when an user does not have any top songs.
     */
    private static final long serialVersionUID = 1L;
    
    public NoTopSongsException(String message) {
        super(message);
    }
}
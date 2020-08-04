package com.b2wdigital.fazemu.exception;

public class FazemuServiceUrlNotFoundException extends FazemuServiceException {

    private static final long serialVersionUID = -6425077391962412864L;

    public FazemuServiceUrlNotFoundException(String msg) {
        super(msg);
    }

    public FazemuServiceUrlNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

}

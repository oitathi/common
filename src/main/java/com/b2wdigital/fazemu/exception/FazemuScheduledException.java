package com.b2wdigital.fazemu.exception;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

public class FazemuScheduledException extends ContextedRuntimeException {

    private static final long serialVersionUID = 7988050188038156704L;

    public FazemuScheduledException(String msg) {
        super(msg);
    }

    public FazemuScheduledException(String msg, Throwable cause) {
        super(msg, cause);
    }

}

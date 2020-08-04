package com.b2wdigital.fazemu.exception;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

public class FazemuServiceException extends ContextedRuntimeException {

    private static final long serialVersionUID = -7983976798079243488L;

    public FazemuServiceException(String msg) {
        super(msg);
    }

    public FazemuServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }

}

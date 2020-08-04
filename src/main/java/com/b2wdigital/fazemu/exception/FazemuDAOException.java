package com.b2wdigital.fazemu.exception;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

public class FazemuDAOException extends ContextedRuntimeException {

    private static final long serialVersionUID = 5521392962909750014L;

    public FazemuDAOException(String msg) {
        super(msg);
    }

    public FazemuDAOException(String msg, Throwable cause) {
        super(msg, cause);
    }

}

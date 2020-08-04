package com.b2wdigital.fazemu.exception;

/**
 *
 * @author dailton.almeida
 */
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6891874435990612839L;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

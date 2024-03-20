package com.springbootwithmvc.exception;

public class ReleaseDetailsNotFoundException extends Exception{
    public ReleaseDetailsNotFoundException() {
        super();
    }

    public ReleaseDetailsNotFoundException(String message) {
        super(message);
    }

    public ReleaseDetailsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReleaseDetailsNotFoundException(Throwable cause) {
        super(cause);
    }

    public ReleaseDetailsNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

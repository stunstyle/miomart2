package com.stunstyle.miomart2.exception;

public class CouldNotAddProductException extends Exception {

    private static final long serialVersionUID = -4768877042453420188L;

    public CouldNotAddProductException(String msg) {
        super(msg);
    }

    public CouldNotAddProductException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

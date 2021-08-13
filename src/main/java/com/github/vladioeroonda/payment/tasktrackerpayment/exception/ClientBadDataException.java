package com.github.vladioeroonda.payment.tasktrackerpayment.exception;

public class ClientBadDataException extends RuntimeException{

    public ClientBadDataException() {
    }

    public ClientBadDataException(String message) {
        super(message);
    }

    public ClientBadDataException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.github.vladioeroonda.payment.tasktrackerpayment.exception;

public class ClientNotFoundException extends RuntimeException{
    public ClientNotFoundException() {
    }

    public ClientNotFoundException(String message) {
        super(message);
    }

    public ClientNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

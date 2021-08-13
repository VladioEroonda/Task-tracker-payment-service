package com.github.vladioeroonda.payment.tasktrackerpayment.exception;

public class TransactionBadDataException extends RuntimeException {
    public TransactionBadDataException() {
    }

    public TransactionBadDataException(String message) {
        super(message);
    }

    public TransactionBadDataException(String message, Throwable cause) {
        super(message, cause);
    }
}

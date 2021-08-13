package com.github.vladioeroonda.payment.tasktrackerpayment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {ClientBadDataException.class, TransactionBadDataException.class})
    public ResponseEntity<Object> handleBadDataException(RuntimeException e) {
        ExceptionTemplate exceptionResponse = new ExceptionTemplate(e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ClientNotFoundException.class, TransactionNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(RuntimeException e) {
        ExceptionTemplate exceptionResponse = new ExceptionTemplate(e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}

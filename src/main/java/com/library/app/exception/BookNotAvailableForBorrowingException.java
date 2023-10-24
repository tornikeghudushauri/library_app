package com.library.app.exception;

public class BookNotAvailableForBorrowingException extends RuntimeException {
    public BookNotAvailableForBorrowingException(String message) {
        super(message);
    }
}

package com.library.app.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String s) {
        super(s);
    }
}

package com.leadingagile.bookstore.model;

public class InvalidBookException extends RuntimeException {
    private String message;

    public InvalidBookException(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}

package com.nhnacademy.exception;

public class InvalidCurrencyException extends IllegalArgumentException {
    public InvalidCurrencyException() {
        super("invalid input");
    }
}

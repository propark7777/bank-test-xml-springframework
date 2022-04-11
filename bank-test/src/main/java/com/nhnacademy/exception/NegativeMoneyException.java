package com.nhnacademy.exception;

public class NegativeMoneyException extends IllegalArgumentException {
    public NegativeMoneyException() {
        super("Money amount can't be negative");
    }
}

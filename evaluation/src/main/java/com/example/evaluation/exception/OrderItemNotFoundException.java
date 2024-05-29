package com.example.evaluation.exception;

public class OrderItemNotFoundException extends Exception {
    public OrderItemNotFoundException(String message) {
        super(message);
    }
}

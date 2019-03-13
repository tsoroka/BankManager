package com.sunhill.task.bankmanager.exception;

public class OperationNotSupportedException extends RuntimeException {

    private String operation;

    public OperationNotSupportedException(String operation) {
        this.operation = operation;
    }

    @Override
    public String getMessage() {
        return String.format("Operation %s not supported", operation);
    }
}

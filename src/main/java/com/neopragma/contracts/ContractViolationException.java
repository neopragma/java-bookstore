package com.neopragma.contracts;

/**
 * Thrown when a Contract.require() or Contract.ensure() assertion fails.
 */
public final class ContractViolationException extends RuntimeException {

    private String message;

    ContractViolationException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

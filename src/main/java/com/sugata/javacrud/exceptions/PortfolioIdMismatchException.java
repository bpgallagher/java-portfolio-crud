package com.sugata.javacrud.exceptions;

public class PortfolioIdMismatchException extends RuntimeException {

    public PortfolioIdMismatchException() {
        super();
    }

    public PortfolioIdMismatchException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PortfolioIdMismatchException(final String message) {
        super(message);
    }

    public PortfolioIdMismatchException(final Throwable cause) {
        super(cause);
    }
}
package com.sugata.javacrud.exceptions;

public class PortfolioNotFoundException extends RuntimeException {

    public PortfolioNotFoundException() {
        super();
    }

    public PortfolioNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PortfolioNotFoundException(final String message) {
        super(message);
    }

    public PortfolioNotFoundException(final Throwable cause) {
        super(cause);
    }
}
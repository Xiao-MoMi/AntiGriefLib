package net.momirealms.antigrieflib;

public class CompatibilityException extends RuntimeException {

    public CompatibilityException(String message) {
        super(message);
    }

    public CompatibilityException(String message, Throwable cause) {
        super(message, cause);
    }
}

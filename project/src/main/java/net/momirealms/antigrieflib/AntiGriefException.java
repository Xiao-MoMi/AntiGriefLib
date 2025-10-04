package net.momirealms.antigrieflib;

public class AntiGriefException extends RuntimeException {

    public AntiGriefException(String message) {
        super(message);
    }

    public AntiGriefException(Throwable cause) {
        super(cause);
    }
}

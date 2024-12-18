package ru.novik.neirofitnessbot.error;

public class SubscriptionCheckException extends RuntimeException {
    public SubscriptionCheckException(String message, Throwable cause) {
        super(message, cause);
    }
}

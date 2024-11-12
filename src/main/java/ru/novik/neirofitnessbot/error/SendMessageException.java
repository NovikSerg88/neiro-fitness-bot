package ru.novik.neirofitnessbot.error;

public class SendMessageException extends RuntimeException {
    public SendMessageException(String message) {
        super(message);
    }
}

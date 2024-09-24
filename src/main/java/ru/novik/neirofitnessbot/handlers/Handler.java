package ru.novik.neirofitnessbot.handlers;

public interface Handler<T> {
    void choose(T t);
}

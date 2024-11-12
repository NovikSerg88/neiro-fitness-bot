package ru.novik.neirofitnessbot.service;

public interface ClientMessageService {

    void deletePreviousMessage(Long chatId);

    void updateMessageId(Long chatId, Integer messageId);
}

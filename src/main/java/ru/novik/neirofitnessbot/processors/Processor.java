package ru.novik.neirofitnessbot.processors;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;

public interface Processor {

    void executeMessage(Message message);

    void executeCallback(CallbackQuery callbackQuery);

    void executePreCheckoutQuery(PreCheckoutQuery preCheckoutQuery);

    default void process(Update update) {
        if (update.hasMessage()) {
            executeMessage(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            executeCallback(update.getCallbackQuery());
        } else if (update.hasPreCheckoutQuery()) {
            executePreCheckoutQuery(update.getPreCheckoutQuery());
        }
    }
}

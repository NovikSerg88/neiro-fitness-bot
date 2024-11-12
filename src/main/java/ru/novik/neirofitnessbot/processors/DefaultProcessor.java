
package ru.novik.neirofitnessbot.processors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import ru.novik.neirofitnessbot.handlers.CallbackQueryHandler;
import ru.novik.neirofitnessbot.handlers.MessageHandler;
import ru.novik.neirofitnessbot.handlers.PreCheckoutQueryHandler;

@Component
@RequiredArgsConstructor
public class DefaultProcessor implements Processor {

    private final MessageHandler messageHandler;
    private final CallbackQueryHandler callbackQueryHandler;
    private final PreCheckoutQueryHandler preCheckoutQueryHandler;

    @Override
    public void executeMessage(Message message) {
        messageHandler.choose(message);
    }

    @Override
    public void executeCallback(CallbackQuery callbackQuery) {
        callbackQueryHandler.choose(callbackQuery);
    }

    @Override
    public void executePreCheckoutQuery(PreCheckoutQuery preCheckoutQuery) {
        preCheckoutQueryHandler.choose(preCheckoutQuery);
    }
}


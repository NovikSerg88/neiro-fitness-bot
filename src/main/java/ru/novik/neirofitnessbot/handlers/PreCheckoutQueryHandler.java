package ru.novik.neirofitnessbot.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import ru.novik.neirofitnessbot.service.MessageService;

@Component
@RequiredArgsConstructor
public class PreCheckoutQueryHandler implements Handler<PreCheckoutQuery> {

    private final MessageService messageService;

    @Override
    public void choose(PreCheckoutQuery preCheckoutQuery) {
        messageService.sendPreCheckoutQuery(preCheckoutQuery);
    }
}

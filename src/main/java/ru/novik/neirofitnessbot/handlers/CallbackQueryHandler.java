package ru.novik.neirofitnessbot.handlers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import ru.novik.neirofitnessbot.keyboards.Keyboards;
import ru.novik.neirofitnessbot.service.SendMessageService;

@Component
@AllArgsConstructor
public class CallbackQueryHandler implements Handler<CallbackQuery> {

    private final SendMessageService sendMessageService;

    @Override
    public void choose(CallbackQuery callbackQuery) {
        String callBackData = callbackQuery.getData();
        Long chatId = callbackQuery.getMessage().getChatId();
        switch (callBackData) {
            case "/start" -> handleStartCallBack(chatId);
        }
    }

    private void handleStartCallBack(Long telegramId) {
        sendMessageService.sendMessage(telegramId, "Привет от кнопки");
    }
}

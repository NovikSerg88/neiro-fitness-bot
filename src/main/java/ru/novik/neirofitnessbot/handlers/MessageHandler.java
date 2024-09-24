
package ru.novik.neirofitnessbot.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.meta.api.objects.message.Message;
import ru.novik.neirofitnessbot.keyboards.Keyboards;
import ru.novik.neirofitnessbot.service.SendMessageService;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageHandler implements Handler<Message> {

    private final SendMessageService sendMessageService;

    @Override
    public void choose(Message message) {
        Long chatId = message.getChatId();
        if (message.hasText()) {
            handleCommands(chatId, message.getText());

        }
    }

    private void handleCommands(Long userId, String text) {
        log.debug("Пользователь с ID = {} отправил команду:{}", userId, text);
        switch (text) {
            case "/start" -> {
                log.info("Команда /start отправлена пользователем с ID = {}", userId);
                sendMessageService.sendMessage(userId, text, Keyboards.replyKeyboard());
            }
            default -> log.warn("Пользователь с ID = {} отправил неизвестную команду: {}", userId, text);
        }
    }
}


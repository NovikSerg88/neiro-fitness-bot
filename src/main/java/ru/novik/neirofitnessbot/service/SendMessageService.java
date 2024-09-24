
package ru.novik.neirofitnessbot.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.novik.neirofitnessbot.keyboards.Keyboards;
import ru.novik.neirofitnessbot.utils.Constants;


@Service
@AllArgsConstructor
@Slf4j
public class SendMessageService {

    private final TelegramClient telegramClient;

    public void sendMessage(Long chatId, String text) {
        log.info("Отправка сообщения пользователю с ID: {}", chatId);
        SendMessage message = SendMessage.builder()
                .text(text)
                .chatId(String.valueOf(chatId))
                .build();
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(Long chatId, String text, ReplyKeyboardMarkup replyKeyboardMarkup) {
        log.info("Отправка сообщения c поддержкой клавиатуры пользователю с ID: {}", chatId);
        SendMessage message = SendMessage.builder()
                .text(Constants.START)
                .chatId(String.valueOf(chatId))
                .replyMarkup(replyKeyboardMarkup)
                .build();
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}


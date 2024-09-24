package ru.novik.neirofitnessbot.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;


import java.util.Collections;


public class Keyboards {

    public static InlineKeyboardMarkup startKeyboard() {
        InlineKeyboardRow row = new InlineKeyboardRow();
        row.add(InlineKeyboardButton.builder()
                .text("Привет")
                .callbackData("/start")
                .build());
        return new InlineKeyboardMarkup(Collections.singletonList(row));
    }

    public static ReplyKeyboardMarkup replyKeyboard() {
        KeyboardRow row = new KeyboardRow();
        row.add("Уроки");
        row.add("Оплатить");
        return ReplyKeyboardMarkup.builder()
                .keyboardRow(row)
                .resizeKeyboard(true)
                .build();
    }
}

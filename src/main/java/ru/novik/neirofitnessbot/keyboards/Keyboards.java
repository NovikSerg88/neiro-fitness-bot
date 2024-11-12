package ru.novik.neirofitnessbot.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.novik.neirofitnessbot.utils.Commands.*;
import static ru.novik.neirofitnessbot.utils.Emojis.SBER_EMOJI;
import static ru.novik.neirofitnessbot.utils.Emojis.YKASSA_EMOJI;

public class Keyboards {

    public static InlineKeyboardMarkup subscribeKeyboard() {
        InlineKeyboardRow row = new InlineKeyboardRow();
        row.add(InlineKeyboardButton.builder()
                .text("1 месяц")
                .callbackData(ONE_MONTH_ACCESS)
                .build());
        row.add(InlineKeyboardButton.builder()
                .text("3 месяца")
                .callbackData(THREE_MONTH_ACCESS)
                .build());
        return new InlineKeyboardMarkup(Collections.singletonList(row));
    }

    public static InlineKeyboardMarkup paymentOptionsKeyboard() {
        InlineKeyboardRow firstRow = new InlineKeyboardRow();
        firstRow.add(InlineKeyboardButton.builder()
                .text(SBER_EMOJI + " СБЕР")
                .callbackData(SBER_PAY)
                .build());
        InlineKeyboardRow secondRow = new InlineKeyboardRow();
        secondRow.add(InlineKeyboardButton.builder()
                .text(YKASSA_EMOJI + " ЮКасса")
                .callbackData(YKASSA_PAY)
                .build());
        List<InlineKeyboardRow> rows = new ArrayList<>();
        rows.add(firstRow);
        rows.add(secondRow);
        return new InlineKeyboardMarkup(rows);
    }

    public static InlineKeyboardMarkup getPrivateChanelKeyboard() {
        InlineKeyboardRow row = new InlineKeyboardRow();
        row.add(InlineKeyboardButton.builder()
                .text("Получить доступ ко всем тренировкам")
                .callbackData("/subscribe")
                .build());
        return new InlineKeyboardMarkup(Collections.singletonList(row));
    }

    public static InlineKeyboardMarkup startKeyboard() {
        InlineKeyboardRow firstRow = new InlineKeyboardRow();
        firstRow.add(InlineKeyboardButton.builder()
                .text("Пробная тренировка")
                .callbackData("/demo")
                .build());

        InlineKeyboardRow secondRow = new InlineKeyboardRow();
        secondRow.add(InlineKeyboardButton.builder()
                .text("Подписаться")
                .callbackData("/subscribe")
                .build());
        List<InlineKeyboardRow> rows = new ArrayList<>();
        rows.add(firstRow);
        rows.add(secondRow);
        return new InlineKeyboardMarkup(rows);
    }
}

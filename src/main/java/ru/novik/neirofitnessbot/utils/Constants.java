package ru.novik.neirofitnessbot.utils;

import java.util.Currency;

public class Constants {

    final static String RUB_SYMBOL = Currency.getInstance("RUB").getSymbol();

    public static final String START_MESSAGE = "👋 *Привет!* Я твой персональный помощник на пути к телу мечты! 💪\n\n" +
            "С моими тренировками ты сможешь:\n" +
            "✨ Избавиться от проблем со здоровьем\n" +
            "✨ Улучшить эмоциональное состояние\n" +
            "✨ Достичь гармонии тела и души\n\n" +
            "Ты можешь:\n" +
            "👉 Познакомиться с методикой тренировок\n" +
            "👉 Подписаться на закрытый канал и получить доступ к уникальной системе занятий!\n\n" +
            "Давай начнем этот путь вместе! 🚀";

    public static final String TYPES_OF_SUBSCRIPTIONS = "✨ *Варианты подписки:* ✨\n\n" +
            "📅 *Один месяц* — 3000 " + RUB_SYMBOL + "\n" +
            "📅 *Три месяца* — 7000 " + RUB_SYMBOL + "\n\n" +
            "💎 *Условия подписки:* 💎\n" +
            "Получите неограниченный доступ к новым тренировкам и архиву видео занятий, " +
            "которые будут доступны весь срок вашей подписки. 🏆\n\n" +
            "Подходит для любого уровня и возраста — начните свой путь к здоровью и гармонии! 🌟";

    public static final String CHOOSE_PAYMENT_METHOD = "Выберите метод оплаты: ";

    public static final String INCORRECT_INPUT = "Неизвестная команда.\nПопробуйте еще раз!";

    public static final String DEMO_VIDEO_CAPTION = "🔥 *Пробная тренировка!* 🔥\n\n" +
            "Начни путь к плоскому животу и гибкости с этой бесплатной тренировкой!\n";

    public static final String SUBSCRIBED = "Вы уже подписаны на канал!\nДата окончания вашей подписки: ";
}


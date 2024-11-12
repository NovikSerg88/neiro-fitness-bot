package ru.novik.neirofitnessbot.service;

import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import org.telegram.telegrambots.meta.api.objects.payments.SuccessfulPayment;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface MessageService {

    void sendMessage(Long chatId, String text, InlineKeyboardMarkup inlineKeyboardMarkup);

    void sendSubscribe(Long chatId);

    void sendPaymentOptions(Long chatId);

    void sendVideo(Long chatId);

    void sendInvoice(Long chatId, int price, String paymentType);

    void sendPreCheckoutQuery(PreCheckoutQuery preCheckoutQuery);

    void servePayment(Long chatId, SuccessfulPayment successfulPayment);

    String sendExportChatInviteLink(String privateChannelId);

    void sendUnbanChatMember(String privateChannelId, Long chatId);
}

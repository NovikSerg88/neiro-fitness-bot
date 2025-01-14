package ru.novik.neirofitnessbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.meta.api.methods.groupadministration.ExportChatInviteLink;
import org.telegram.telegrambots.meta.api.methods.groupadministration.UnbanChatMember;
import org.telegram.telegrambots.meta.api.methods.invoices.SendInvoice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendMessageServiceImpl implements SendMessageService {

    private final TelegramClient telegramClient;

    @Override
    public Integer sendMessage(SendMessage sendMessage) {
        try {
            log.info("Received message {} from chat id: {}", sendMessage.getText(), sendMessage.getChatId());
            return telegramClient.execute(sendMessage).getMessageId();
        } catch (TelegramApiException e) {
            throw new RuntimeException("An error occurred while sending the message", e);
        }
    }

    @Override
    public Integer sendInvoice(SendInvoice sendInvoice) {
        try {
            log.info("Received invoice message from chat id: {}", sendInvoice.getChatId());
            return telegramClient.execute(sendInvoice).getMessageId();
        } catch (TelegramApiException e) {
            throw new RuntimeException("An error occurred while sending the message", e);
        }
    }

    @Override
    public void sendVideo(SendVideo sendVideo) {
        try {
            log.info("Received media message from chat id: {}", sendVideo.getChatId());
            telegramClient.execute(sendVideo);
        } catch (TelegramApiException e) {
            throw new RuntimeException("An error occurred while sending the message", e);
        }
    }

    @Override
    public void sendAnswerPreCheckoutQuery(AnswerPreCheckoutQuery answerPreCheckoutQuery) {
        try {
            log.info("Received answerPreCheckoutQuery with id: {}", answerPreCheckoutQuery.getPreCheckoutQueryId());
            telegramClient.execute(answerPreCheckoutQuery);
        } catch (TelegramApiException e) {
            log.error("AnswerPreCheckoutQuery error : {}", answerPreCheckoutQuery.getErrorMessage());
            throw new RuntimeException("An error occurred while sending the message", e);
        }
    }

    @Override
    public void deleteMessage(DeleteMessage deleteMessage) {
        try {
            log.info("Received delete message from chat id: {}", deleteMessage.getChatId());
            telegramClient.execute(deleteMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("An error occurred while deleting the message", e);
        }
    }

    @Override
    public String getChannelInviteLink(ExportChatInviteLink exportInviteLink) {
        try {
            log.info("Received invite link to channel with id: {}", exportInviteLink.getChatId());
            return telegramClient.execute(exportInviteLink);
        } catch (TelegramApiException e) {
            throw new RuntimeException("An error occurred while sending the message", e);
        }
    }

    @Override
    public void grantAccessToChannel(UnbanChatMember unbanChatMember) {
        log.info("Received message granted access to channel for user with chat id : {}", unbanChatMember.getChatId());
        try {
            telegramClient.execute(unbanChatMember);
        } catch (TelegramApiException e) {
            throw new RuntimeException("An error occurred while sending the message", e);
        }
    }
}

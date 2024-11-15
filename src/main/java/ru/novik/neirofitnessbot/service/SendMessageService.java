package ru.novik.neirofitnessbot.service;

import org.telegram.telegrambots.meta.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.meta.api.methods.groupadministration.ExportChatInviteLink;
import org.telegram.telegrambots.meta.api.methods.groupadministration.UnbanChatMember;
import org.telegram.telegrambots.meta.api.methods.invoices.SendInvoice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;

public interface SendMessageService {

    Integer sendMessage(SendMessage sendMessage);

    Integer sendInvoice(SendInvoice sendInvoice);

    void sendVideo(SendVideo sendVideo);

    void sendAnswerPreCheckoutQuery(AnswerPreCheckoutQuery answerPreCheckoutQuery);

    void deleteMessage(DeleteMessage deleteMessage);

    String getChannelInviteLink(ExportChatInviteLink exportInviteLink);

    void grantAccessToChannel(UnbanChatMember unbanChatMember);

}

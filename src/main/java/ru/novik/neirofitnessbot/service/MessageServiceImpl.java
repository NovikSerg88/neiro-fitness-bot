package ru.novik.neirofitnessbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.meta.api.methods.groupadministration.ExportChatInviteLink;
import org.telegram.telegrambots.meta.api.methods.groupadministration.UnbanChatMember;
import org.telegram.telegrambots.meta.api.methods.invoices.SendInvoice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import org.telegram.telegrambots.meta.api.objects.payments.SuccessfulPayment;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.novik.neirofitnessbot.keyboards.Keyboards;
import ru.novik.neirofitnessbot.utils.Constants;

import static ru.novik.neirofitnessbot.utils.Commands.SBER_PAY;
import static ru.novik.neirofitnessbot.utils.Commands.YKASSA_PAY;
import static ru.novik.neirofitnessbot.utils.Constants.DEMO_VIDEO_CAPTION;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final ClientMessageService clientMessageService;
    private final SendMessageService sendMessageService;

    @Value("${video.file_id}")
    private String demoVideoFileId;
    @Value("${telegram.private_channel_id}")
    private String privateChannelId;
    @Value("${payment.sber}")
    private String sberPayToken;
    @Value("${payment.ykassa}")
    private String yKassaToken;

    @Override
    public void sendMessage(Long chatId, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage sendMessage = SendMessage.builder()
                .text(text)
                .chatId(String.valueOf(chatId))
                .replyMarkup(inlineKeyboardMarkup)
                .parseMode("Markdown")
                .build();
        Integer messageId = sendMessageService.sendMessage(sendMessage);
        clientMessageService.updateMessageId(chatId, messageId);
    }

    @Override
    public void sendSubscribe(Long chatId) {
        SendMessage sendMessage = SendMessage.builder()
                .text(Constants.TYPES_OF_SUBSCRIPTIONS)
                .chatId(String.valueOf(chatId))
                .replyMarkup(Keyboards.subscribeKeyboard())
                .parseMode("Markdown")
                .build();
        clientMessageService.deletePreviousMessage(chatId);
        Integer messageId = sendMessageService.sendMessage(sendMessage);
        clientMessageService.updateMessageId(chatId, messageId);
    }

    @Override
    public void sendPaymentOptions(Long chatId) {
        SendMessage sendMessage = SendMessage.builder()
                .text(Constants.CHOOSE_PAYMENT_METHOD)
                .chatId(String.valueOf(chatId))
                .replyMarkup(Keyboards.paymentOptionsKeyboard())
                .parseMode("Markdown")
                .build();
        clientMessageService.deletePreviousMessage(chatId);
        Integer messageId = sendMessageService.sendMessage(sendMessage);
        clientMessageService.updateMessageId(chatId, messageId);
    }

    @Override
    public void sendVideo(Long chatId) {
        SendVideo video = SendVideo.builder()
                .chatId(chatId)
                .replyMarkup(Keyboards.getPrivateChanelKeyboard())
                .video(new InputFile(demoVideoFileId))
                .caption(DEMO_VIDEO_CAPTION)
                .parseMode("Markdown")
                .build();
        sendMessageService.sendVideo(video);
    }

    @Override
    public void sendInvoice(Long chatId, int amount, String paymentType) {
        String providerToken = switch (paymentType) {
            case SBER_PAY -> sberPayToken;
            case YKASSA_PAY -> yKassaToken;
            default -> throw new IllegalStateException("Unexpected value: " + paymentType);
        };
        SendInvoice sendInvoice = SendInvoice.builder()
                .chatId(chatId)
                .title("Полный курс")
                .description("Коллекция видео тренировок в домашних условиях")
                .payload("neiro_full_course")
                .providerToken(providerToken)
                .currency("RUB")
                .price(new LabeledPrice("label", amount))
                .build();
        clientMessageService.deletePreviousMessage(chatId);
        Integer messageId = sendMessageService.sendInvoice(sendInvoice);
        clientMessageService.updateMessageId(chatId, messageId);
    }

    @Override
    public void sendPreCheckoutQuery(PreCheckoutQuery preCheckoutQuery) {
        AnswerPreCheckoutQuery answerPreCheckoutQuery = AnswerPreCheckoutQuery.builder()
                .preCheckoutQueryId(preCheckoutQuery.getId())
                .ok(true)
                .build();
        sendMessageService.sendAnswerPreCheckoutQuery(answerPreCheckoutQuery);
    }

    @Override
    public void servePayment(Long chatId, SuccessfulPayment successfulPayment) {
        String inviteLink = sendExportChatInviteLink(privateChannelId);
        sendUnbanChatMember(privateChannelId, chatId);
        SendMessage sendMessage = SendMessage.builder()
                .text("Ссылка на закрытый канал : \n" + inviteLink)
                .chatId(String.valueOf(chatId))
                .parseMode("Markdown")
                .build();

        clientMessageService.deletePreviousMessage(chatId);
        Integer messageId = sendMessageService.sendMessage(sendMessage);
        clientMessageService.updateMessageId(chatId, messageId);
    }

    @Override
    public String sendExportChatInviteLink(String privateChannelId) {
        ExportChatInviteLink exportInviteLink = ExportChatInviteLink.builder()
                .chatId(privateChannelId)
                .build();
        return sendMessageService.getChannelInviteLink(exportInviteLink);
    }

    @Override
    public void sendUnbanChatMember(String privateChannelId, Long chatId) {
        UnbanChatMember unbanChatMember = UnbanChatMember.builder()
                .chatId(privateChannelId)
                .userId(chatId)
                .build();
        sendMessageService.grantAccessToChannel(unbanChatMember);
    }
}

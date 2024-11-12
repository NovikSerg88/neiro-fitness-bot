package ru.novik.neirofitnessbot.handlers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.novik.neirofitnessbot.keyboards.Keyboards;
import ru.novik.neirofitnessbot.model.Client;
import ru.novik.neirofitnessbot.model.SubscriptionOption;
import ru.novik.neirofitnessbot.service.*;
import java.time.format.DateTimeFormatter;

import static ru.novik.neirofitnessbot.utils.Commands.*;
import static ru.novik.neirofitnessbot.utils.Constants.SUBSCRIBED;

@Component
@AllArgsConstructor
public class CallbackQueryHandler implements Handler<CallbackQuery> {

    private final MessageService messageService;
    private final ClientService clientService;

    @Override
    public void choose(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        String callBackData = callbackQuery.getData();
        switch (callBackData) {
            case DEMO -> handleDemo(chatId);
            case SBER_PAY, TBANK_PAY, YKASSA_PAY -> handlePayCallBack(chatId, callBackData);
            case SUBSCRIBE -> handleSubscribe(chatId);
            case ONE_MONTH_ACCESS, THREE_MONTH_ACCESS -> handlePaymentMethod(chatId, callBackData);
        }
    }

    private void handleDemo(Long chatId) {
        messageService.sendVideo(chatId);
    }

    private void handleSubscribe(Long chatId) {
        Client client = clientService.findById(chatId).orElseThrow(() ->
                new RuntimeException("Client not found"));
        if (client.isSubscribed()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm");
            String subscriptionEndDate = client.getEndDate().format(formatter);
            messageService.sendMessage(chatId, SUBSCRIBED + subscriptionEndDate, Keyboards.startKeyboard());
        } else {
            messageService.sendSubscribe(chatId);
        }
    }


    private void handlePayCallBack(Long chatId, String paymentType) {
        Client client = clientService.findById(chatId).orElseThrow(() ->
                new RuntimeException("Client not found"));
        int price = 0;
        if (SubscriptionOption.ONE_MONTH.equals(client.getSubscription())) {
            price = 300000;
        } else if (SubscriptionOption.THREE_MONTH.equals(client.getSubscription())) {
            price = 700000;
        }
        messageService.sendInvoice(chatId, price, paymentType);
    }

    private void handlePaymentMethod(Long chatId, String callBackData) {
        Client client = clientService.findById(chatId).orElseThrow(() ->
                new RuntimeException("Client not found"));
        if (callBackData.equals(ONE_MONTH_ACCESS)) {
            client.setSubscription(SubscriptionOption.ONE_MONTH);
        } else {
            client.setSubscription(SubscriptionOption.THREE_MONTH);
        }
        clientService.saveClient(client);
        messageService.sendPaymentOptions(chatId);
    }
}

package ru.novik.neirofitnessbot.handlers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.novik.neirofitnessbot.error.NotFoundException;
import ru.novik.neirofitnessbot.keyboards.Keyboards;
import ru.novik.neirofitnessbot.model.Client;
import ru.novik.neirofitnessbot.model.SubscriptionOption;
import ru.novik.neirofitnessbot.service.*;
import ru.novik.neirofitnessbot.utils.DateUtils;

import static ru.novik.neirofitnessbot.utils.Commands.*;
import static ru.novik.neirofitnessbot.utils.Constants.SUBSCRIBED;
import static ru.novik.neirofitnessbot.utils.PriceConstants.ONE_MONTH_PRICE;
import static ru.novik.neirofitnessbot.utils.PriceConstants.THREE_MONTH_PRICE;

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
                new NotFoundException(String.format("Client with id %s not found", chatId)));
        if (client.isSubscribed()) {
            String subscriptionEndDate = DateUtils.formatDate(client.getEndDate());
            messageService.sendMessage(chatId, SUBSCRIBED + subscriptionEndDate, Keyboards.startKeyboard());
        } else {
            messageService.sendSubscribe(chatId);
        }
    }


    private void handlePayCallBack(Long chatId, String paymentType) {
        Client client = clientService.findById(chatId).orElseThrow(() ->
                new NotFoundException(String.format("Client with id %s not found", chatId)));
        int price = getPrice(client.getSubscription());
        messageService.sendInvoice(chatId, price, paymentType);
    }

    private void handlePaymentMethod(Long chatId, String callBackData) {
        Client client = clientService.findById(chatId).orElseThrow(() ->
                new NotFoundException(String.format("Client with id %s not found", chatId)));
        if (callBackData.equals(ONE_MONTH_ACCESS)) {
            client.setSubscription(SubscriptionOption.ONE_MONTH);
        } else {
            client.setSubscription(SubscriptionOption.THREE_MONTH);
        }
        clientService.saveClient(client);
        messageService.sendPaymentOptions(chatId);
    }

    private int getPrice(SubscriptionOption option) {
        return switch (option) {
            case ONE_MONTH -> ONE_MONTH_PRICE;
            case THREE_MONTH -> THREE_MONTH_PRICE;
            default -> throw new IllegalStateException("Unexpected value: " + option);
        };
    }
}

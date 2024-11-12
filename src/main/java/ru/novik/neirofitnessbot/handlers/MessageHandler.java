
package ru.novik.neirofitnessbot.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.payments.SuccessfulPayment;
import ru.novik.neirofitnessbot.keyboards.Keyboards;
import ru.novik.neirofitnessbot.model.Client;
import ru.novik.neirofitnessbot.model.SubscriptionOption;
import ru.novik.neirofitnessbot.service.ClientService;
import ru.novik.neirofitnessbot.service.MessageService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ru.novik.neirofitnessbot.utils.Commands.*;
import static ru.novik.neirofitnessbot.utils.Constants.*;

@Component
@RequiredArgsConstructor
public class MessageHandler implements Handler<Message> {

    private final MessageService messageService;
    private final ClientService clientService;

    @Override
    public void choose(Message message) {
        Long chatId = message.getChatId();
        String firstName = message.getFrom().getFirstName();
        String userName = message.getFrom().getUserName();
        String messageText = message.getText();
        if (message.hasText()) {
            handleCommands(chatId, messageText, firstName, userName);
        } else if (message.hasSuccessfulPayment()) {
            SuccessfulPayment successfulPayment = message.getSuccessfulPayment();
            handleSuccessfulPayment(chatId, successfulPayment);
        }
    }

    private void handleCommands(Long chatId, String messageText, String firstName, String userName) {
        if (START.equals(messageText)) {
            clientService.findById(chatId).orElseGet(() -> {
                Client client = Client.builder()
                        .chatId(chatId)
                        .firstName(firstName)
                        .userName(userName)
                        .subscription(SubscriptionOption.DEMO)
                        .build();
                return clientService.saveClient(client);
            });
            messageService.sendMessage(chatId, START_MESSAGE, Keyboards.startKeyboard());
        } else if (DEMO.equals(messageText)) {
            handleDemo(chatId);
        } else if (SUBSCRIBE.equals(messageText)) {
            handleSubscribe(chatId);
        } else {
            messageService.sendMessage(chatId, INCORRECT_INPUT, Keyboards.startKeyboard());
        }
    }

    private void handleSuccessfulPayment(Long chatId, SuccessfulPayment successfulPayment) {
        clientService.findById(chatId).ifPresent(client -> {
            client.setStartDate(LocalDateTime.now());
            if (SubscriptionOption.ONE_MONTH.equals(client.getSubscription())) {
                client.setEndDate(LocalDateTime.now().plusMonths(1));
            } else if (SubscriptionOption.THREE_MONTH.equals(client.getSubscription())) {
                client.setEndDate(LocalDateTime.now().plusMonths(3));
            }
            client.setSubscribed(true);
            clientService.saveClient(client);
        });
        messageService.servePayment(chatId, successfulPayment);
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
}


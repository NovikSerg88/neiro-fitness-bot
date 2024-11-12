package ru.novik.neirofitnessbot.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.groupadministration.BanChatMember;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.novik.neirofitnessbot.model.Client;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionCheckService {

    private final ClientService clientService;
    private final TelegramClient telegramClient;
    private final ScheduledExecutorService scheduler;

    @Value("${telegram.private_channel_id}")
    private String privateChannelId;

    @PostConstruct
    public void init() {
        Runnable task = () -> {
            try {
                checkSubscription();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        };
        scheduler.scheduleAtFixedRate(
                task,
                0,
                5,
                TimeUnit.MINUTES
        );
    }

    private void checkSubscription() {
        List<Client> clients = clientService.findAllSubscribers();
        clients.parallelStream().forEach(client -> {
            if (client.getEndDate().isAfter(LocalDateTime.now())) {
                removeUserFromChannel(client.getChatId());
            }
        });
    }

    private void removeUserFromChannel(Long chatId) {
        log.info("Ban chat member with id {}", chatId);
        try {
            BanChatMember banChatMember = new BanChatMember(privateChannelId, chatId);
            telegramClient.execute(banChatMember);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void shutdown() {
        scheduler.shutdown();
        log.info("Subscription check task stopped");
    }
}

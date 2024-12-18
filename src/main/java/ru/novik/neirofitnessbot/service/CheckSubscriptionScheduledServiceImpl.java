package ru.novik.neirofitnessbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.novik.neirofitnessbot.error.SubscriptionCheckException;
import ru.novik.neirofitnessbot.model.Client;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class CheckSubscriptionScheduledServiceImpl implements CheckSubscriptionService {

    private final ClientService clientService;
    private final SendMessageService sendMessageService;

    @Scheduled(fixedRateString = "${scheduler.rate}", timeUnit = TimeUnit.MINUTES)
    public void checkSubscription() {
        List<Client> clients = clientService.findAllSubscribers();
        clients.forEach(client -> {
            try {
                if (client.getEndDate().isBefore(LocalDateTime.now())) {
                    sendMessageService.removeUserFromChannel(client.getChatId());
                }
            } catch (Exception e) {
                throw new SubscriptionCheckException("Error checking subscription for client id " + client.getChatId(), e);
            }
        });
    }
}

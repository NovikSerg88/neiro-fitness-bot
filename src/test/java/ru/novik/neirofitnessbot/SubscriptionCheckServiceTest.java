package ru.novik.neirofitnessbot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.novik.neirofitnessbot.model.Client;
import ru.novik.neirofitnessbot.service.ClientService;
import ru.novik.neirofitnessbot.service.CheckSubscriptionScheduledServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)

public class SubscriptionCheckServiceTest {

    @Mock
    private ClientService clientService;

    @Mock
    private Executor scheduler;

    @InjectMocks
    private CheckSubscriptionScheduledServiceImpl checkSubscriptionScheduledServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Инициализация моков
    }


    @Test
    @Disabled
    void testCheckSubscription() {
        // Подготовка тестовых данных
        Client activeClient = Client.builder()
                .id(1L)
                .chatId(12345L)
                .firstName("John")
                .userName("john_doe")
                .startDate(LocalDateTime.now().minusDays(30))
                .endDate(LocalDateTime.now().plusDays(1)) // Подписка активна
                .isSubscribed(true)
                .build();

        Client expiredClient = Client.builder()
                .id(2L)
                .chatId(67890L)
                .firstName("Jane")
                .userName("jane_doe")
                .startDate(LocalDateTime.now().minusDays(60))
                .endDate(LocalDateTime.now().minusDays(1)) // Подписка истекла
                .isSubscribed(true)
                .build();

        List<Client> clients = Arrays.asList(activeClient, expiredClient);
        when(clientService.findAllSubscribers()).thenReturn(clients);

        // Мокировать Executor для синхронного выполнения
        doAnswer(invocation -> {
            Runnable runnable = invocation.getArgument(0);
            runnable.run();
            return null;
        }).when(scheduler).execute(any(Runnable.class));

        // Вызов тестируемого метода

    }

    @Test
    @Disabled
    void testCheckSubscriptionExceptionHandling() {
        // Подготовка тестовых данных

        // Убедиться, что ошибка логируется или обрабатывается
        // (добавьте проверку логгера, если он используется)
    }
}


package ru.novik.neirofitnessbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import ru.novik.neirofitnessbot.model.Client;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientMessageServiceImpl implements ClientMessageService {

    private final ClientService clientService;
    private final SendMessageService sendMessageService;

    @Override
    public void deletePreviousMessage(Long chatId) {
        Client client = clientService.findById(chatId).orElseThrow(() -> new RuntimeException("Client not found"));
        if (client.getMessageId() != null) {
            DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(chatId), client.getMessageId());
            sendMessageService.deleteMessage(deleteMessage);
        }
    }

    @Override
    public void updateMessageId(Long chatId, Integer messageId) {
        Client client = clientService.findById(chatId).orElseThrow(() -> new RuntimeException("Client not found"));
        client.setMessageId(messageId);
        clientService.saveClient(client);
    }
}

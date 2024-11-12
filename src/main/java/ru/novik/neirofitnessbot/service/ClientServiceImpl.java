package ru.novik.neirofitnessbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.novik.neirofitnessbot.model.Client;
import ru.novik.neirofitnessbot.repositories.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> findById(Long chatId) {
        return clientRepository.findByChatId(chatId);
    }

    @Override
    public List<Client> findAllSubscribers() {
        return clientRepository.findAllByIsSubscribedTrue();
    }
}

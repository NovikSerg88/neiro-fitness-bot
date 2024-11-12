package ru.novik.neirofitnessbot.service;

import ru.novik.neirofitnessbot.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Client saveClient(Client client);

    Optional<Client> findById(Long id);

    List<Client> findAllSubscribers();
}


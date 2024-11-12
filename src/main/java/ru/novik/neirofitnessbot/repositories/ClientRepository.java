package ru.novik.neirofitnessbot.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.novik.neirofitnessbot.model.Client;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByChatId(Long chatId);

    @Transactional
    List<Client> findAllByIsSubscribedTrue();
}

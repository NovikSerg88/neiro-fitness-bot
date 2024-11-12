package ru.novik.neirofitnessbot;

import org.springframework.stereotype.Component;

import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.novik.neirofitnessbot.config.BotConfiguration;
import ru.novik.neirofitnessbot.processors.Processor;

@Component
public class NeiroFitnessBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final BotConfiguration botConfiguration;
    private final Processor processor;

    public NeiroFitnessBot(BotConfiguration botConfiguration, Processor processor) {
        this.botConfiguration = botConfiguration;
        this.processor = processor;
    }

    @Override
    public String getBotToken() {
        return botConfiguration.getBotToken();
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        processor.process(update);
    }
}

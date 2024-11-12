package ru.novik.neirofitnessbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Configuration
public class SchedulerConfig {

    @Bean(destroyMethod = "shutdown")
    public ScheduledExecutorService scheduler() {
        return new ScheduledThreadPoolExecutor(1);
    }
}

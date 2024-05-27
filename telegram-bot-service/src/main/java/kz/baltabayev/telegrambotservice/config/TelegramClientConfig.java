package kz.baltabayev.telegrambotservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Configuration
public class TelegramClientConfig {

    @Value("${bot.token.api}")
    private String token;

    @Bean(name = "telegramClient")
    public TelegramClient telegramClient() {
        return new OkHttpTelegramClient(token);
    }
}
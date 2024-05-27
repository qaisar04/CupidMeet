package kz.baltabayev.telegrambotservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TelegramBotServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotServiceApplication.class, args);
    }
}
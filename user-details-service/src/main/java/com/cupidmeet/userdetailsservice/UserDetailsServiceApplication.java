package com.cupidmeet.userdetailsservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.cupidmeet")
@OpenAPIDefinition(info = @Info(title = "Сервис \"User Details\"", version = "1.0"))
@SuppressWarnings({"checkstyle:UncommentedMain", "checkstyle:HideUtilityClassConstructor"})
@EnableScheduling
public class UserDetailsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserDetailsServiceApplication.class, args);
    }
}
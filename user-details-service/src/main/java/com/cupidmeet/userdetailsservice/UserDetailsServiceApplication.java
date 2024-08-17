package com.cupidmeet.userdetailsservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.cupidmeet")
@OpenAPIDefinition(info = @Info(title = "Сервис \"User Details\"", version = "1.0"))
@SuppressWarnings({"checkstyle:UncommentedMain", "checkstyle:HideUtilityClassConstructor"})
public class UserDetailsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserDetailsServiceApplication.class, args);
    }
}
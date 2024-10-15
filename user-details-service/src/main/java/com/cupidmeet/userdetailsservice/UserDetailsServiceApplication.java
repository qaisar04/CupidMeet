package com.cupidmeet.userdetailsservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.cupidmeet")
@OpenAPIDefinition(
        info = @Info(title = "Сервис \"User Details\"", version = "1.0"),
        security = @SecurityRequirement(name = "bearerToken")
)
@SuppressWarnings({"checkstyle:UncommentedMain", "checkstyle:HideUtilityClassConstructor"})
@SecurityScheme(
        name = "bearerToken",
        type = SecuritySchemeType.OAUTH2,
        scheme = "bearer",
        bearerFormat = "JWT",
        flows = @OAuthFlows(authorizationCode = @OAuthFlow(
                authorizationUrl = "https://qaisar.online:8843/realms/cupid-meet-dev/protocol/openid-connect/auth",
                tokenUrl = "https://qaisar.online:8843/realms/cupid-meet-dev/protocol/openid-connect/token"))
)
public class UserDetailsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserDetailsServiceApplication.class, args);
    }
}
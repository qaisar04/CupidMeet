package kz.baltabayev.authservice.controller;

import kz.baltabayev.authservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    public final AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<?> generate(@RequestParam long userId) {
        long securityCode = authenticationService.generateSecurityCode(userId);
        return ResponseEntity.ok(securityCode);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validate(@RequestParam long securityCode) {
        String token = authenticationService.validate(securityCode);
        return ResponseEntity.ok(token);
    }
}

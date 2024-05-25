package kz.baltabayev.authservice.controller;

import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/generate")
    public ResponseEntity<?> generate(@RequestParam long userId) {
        long securityCode = authenticationService.generateSecurityCode(userId);
        return ResponseEntity.ok(securityCode);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam long securityCode, HttpSession session) {
        String token = authenticationService.validate(securityCode);
        session.setAttribute("token", token);
        return ResponseEntity.ok(token);
    }
}
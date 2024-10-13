package com.cupidmeet.qrservice.controller;

import com.cupidmeet.qrservice.service.QrService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/qr")
@RequiredArgsConstructor
public class QrController {

    private final QrService qrService;

    @GetMapping("/generate")
    @Operation(summary = "Генерирует QR-код для предоставленной ссылки.")
    CompletableFuture<Void> generateQR(
            HttpServletResponse response,
            @RequestParam String link
    ) {
        return qrService.generateQRAsync(response, link);
    }

    @GetMapping("/generate/upload")
    @Operation(summary = "Генерирует QR-код для предоставленной ссылки и возвращает путь к файлу.")
    public String generateQR(@RequestParam String link) {
        return qrService.generateQRPath(link);
    }
}

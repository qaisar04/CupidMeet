package com.cupidmeet.qrservice.service;

import jakarta.servlet.http.HttpServletResponse;

import java.util.concurrent.CompletableFuture;

public interface QrService {

    /**
     * Асинхронно генерирует QR-код для предоставленной ссылки и записывает его
     *
     * @param response HttpServletResponse, в который будет записано сгенерированное изображение QR-кода.
     * @param link     Ссылка или данные, для которых должен быть сгенерирован QR-код.
     */
    CompletableFuture<Void> generateQRAsync(HttpServletResponse response, String link);

    /**
     * Генерирует QR-код для предоставленной ссылки и возвращает его путь.
     *
     * @param link Ссылка или данные, для которых должен быть сгенерирован QR-код.
     * @return Массив байтов, представляющий изображение QR-кода.
     */
    String generateQRPath(String link);
}

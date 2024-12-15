package com.cupidmeet.chatservice.service;

import com.cupidmeet.chatservice.domain.dto.ChatCreateRequest;
import com.cupidmeet.chatservice.domain.dto.ChatResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Интерфейс сервиса чата.
 */
public interface ChatService {

    /**
     * Создание чата.
     *
     * @param request запрос на создание чата
     * @return ответ с созданным чатом
     */
    ChatResponse createChat(ChatCreateRequest request);

    /**
     * Получение чата по идентификатору.
     *
     * @param chatId идентификатор чата
     * @return чат
     */
    ChatResponse getChatById(UUID chatId);

    /**
     * Получение чатов пользователя.
     *
     * @param userId   идентификатор пользователя
     * @param pageable параметры страницы
     * @return список чатов
     */
    Page<ChatResponse> getUserChats(UUID userId, Pageable pageable);

    /**
     * Удаление чата.
     *
     * @param chatId идентификатор чата
     */
    void deleteChat(UUID chatId);

    /**
     * Проверка, входит ли пользователь в чат.
     *
     * @param chatId идентификатор чата
     * @param userId идентификатор пользователя
     * @return признак наличия пользователя в чате
     */
    boolean isUserInChat(UUID chatId, UUID userId);
}

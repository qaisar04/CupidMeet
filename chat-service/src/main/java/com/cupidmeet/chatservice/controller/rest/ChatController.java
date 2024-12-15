package com.cupidmeet.chatservice.controller.rest;

import com.cupidmeet.chatservice.domain.dto.ChatAddParticipantsRequest;
import com.cupidmeet.chatservice.domain.dto.ChatCreateRequest;
import com.cupidmeet.chatservice.domain.dto.ChatResponse;
import com.cupidmeet.chatservice.service.ChatService;
import com.cupidmeet.chatservice.service.UserService;
import com.cupidmeet.chatservice.websocket.event.ChatDeletedEvent;
import com.cupidmeet.chatservice.websocket.event.ParticipantAddedEvent;
import com.cupidmeet.chatservice.websocket.event.ParticipantRemovedEvent;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/chats")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Создание нового чата.
     *
     * @param request запрос на создание чата
     * @return ответ с созданным чатом
     */
    @PostMapping
    @Operation(summary = "Создать чат", description = "Создаёт новый чат")
    public ChatResponse createChat(@RequestBody ChatCreateRequest request) {
        ChatResponse chat = chatService.createChat(request);

        request.getParticipants().forEach(participantId ->
                messagingTemplate.convertAndSend("/queue/user/" + participantId, chat)
        );

        return chat;
    }

    /**
     * Получение чата по идентификатору.
     *
     * @param chatId идентификатор чата
     * @return данные чата
     */
    @GetMapping("/{chatId}")
    @Operation(summary = "Получить чат", description = "Возвращает данные чата по идентификатору")
    public ChatResponse getChatById(@PathVariable UUID chatId) {
        return chatService.getChatById(chatId);
    }

    /**
     * Получение чатов пользователя с пагинацией.
     *
     * @param userId идентификатор пользователя
     * @param pageable параметры пагинации
     * @return список чатов
     */
    @GetMapping
    @Operation(summary = "Получить чаты пользователя", description = "Возвращает список чатов, в которых участвует пользователь")
    public Page<ChatResponse> getUserChats(@RequestParam UUID userId, Pageable pageable) {
        return chatService.getUserChats(userId, pageable);
    }

    /**
     * Удаление чата.
     *
     * @param chatId идентификатор чата
     */
    @DeleteMapping("/{chatId}")
    @Operation(summary = "Удалить чат", description = "Удаляет чат по идентификатору")
    public void deleteChat(@PathVariable UUID chatId) {
        ChatResponse chat = chatService.getChatById(chatId);
        chatService.deleteChat(chatId);

        chat.getParticipants().forEach(participant ->
                messagingTemplate.convertAndSend("/queue/user/" + participant,
                        new ChatDeletedEvent(chatId))
        );
    }
}

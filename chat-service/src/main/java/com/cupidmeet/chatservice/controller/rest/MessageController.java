package com.cupidmeet.chatservice.controller.rest;

import com.cupidmeet.chatservice.domain.dto.MessageResponse;
import com.cupidmeet.chatservice.websocket.event.MessageDeletedEvent;
import com.cupidmeet.chatservice.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/messages")
public class MessageController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Получение сообщений чата с пагинацией.
     *
     * @param chatId идентификатор чата
     * @param pageable параметры пагинации
     * @return страница сообщений
     */
    @GetMapping("/{chatId}")
    @Operation(summary = "Получить сообщения чата", description = "Возвращает сообщения чата с учетом пагинации")
    public Page<MessageResponse> getMessages(@PathVariable UUID chatId, Pageable pageable) {
        return messageService.getMessages(chatId, pageable);
    }

    /**
     * Удаление сообщения по идентификатору.
     *
     * @param messageId идентификатор сообщения
     */
    @DeleteMapping("/{messageId}")
    @Operation(summary = "Удалить сообщение", description = "Удаляет сообщение по его идентификатору")
    public void deleteMessage(@PathVariable UUID messageId) {
        UUID chatId = messageService.getChatIdByMessageId(messageId);
        messageService.deleteMessage(messageId);

        messagingTemplate.convertAndSend("/topic/chat/" + chatId, new MessageDeletedEvent(messageId));
    }

    /**
     * Подсчёт непрочитанных сообщений для пользователя в чате.
     *
     * @param chatId идентификатор чата
     * @param userId идентификатор пользователя
     * @return количество непрочитанных сообщений
     */
    @GetMapping("/{chatId}/unread/count")
    @Operation(summary = "Подсчитать непрочитанные сообщения", description = "Возвращает количество непрочитанных сообщений для пользователя в указанном чате")
    public long countUnreadMessages(@PathVariable UUID chatId, @RequestParam UUID userId) {
        return messageService.countUnreadMessages(chatId, userId);
    }
}

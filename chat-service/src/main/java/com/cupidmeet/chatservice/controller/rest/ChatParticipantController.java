package com.cupidmeet.chatservice.controller.rest;

import com.cupidmeet.chatservice.domain.dto.ChatAddParticipantsRequest;
import com.cupidmeet.chatservice.domain.dto.ChatParticipantResponse;
import com.cupidmeet.chatservice.domain.enumeration.ParticipantRole;
import com.cupidmeet.chatservice.service.ChatParticipantService;
import com.cupidmeet.chatservice.service.UserService;
import com.cupidmeet.chatservice.websocket.event.ParticipantAddedEvent;
import com.cupidmeet.chatservice.websocket.event.ParticipantRemovedEvent;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/chat-participants")
public class ChatParticipantController {

    private final ChatParticipantService chatParticipantService;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;

    /**
     * Получение списка участников чата.
     *
     * @param chatId идентификатор чата
     * @return список участников
     */
    @GetMapping("/{chatId}")
    @Operation(summary = "Получить участников чата", description = "Возвращает список участников указанного чата")
    public List<ChatParticipantResponse> getParticipants(@PathVariable UUID chatId) {
        return chatParticipantService.getParticipants(chatId);
    }

    /**
     * Добавление участников в чат.
     *
     * @param chatId идентификатор чата
     * @param request запрос на добавление участников
     */
    @PostMapping("/{chatId}")
    @Operation(summary = "Добавить участников в чат", description = "Добавляет новых участников в указанный чат")
    public void addParticipants(@PathVariable UUID chatId, @RequestBody ChatAddParticipantsRequest request) {
        chatParticipantService.addParticipants(chatId, request);
        UUID initiatorId = userService.getCurrentId().get();
        request.getUserIds().forEach(userId ->
                messagingTemplate.convertAndSend("/topic/chat/" + chatId,
                        new ParticipantAddedEvent(chatId, userId, initiatorId))
        );
    }

    /**
     * Обновление роли участника чата.
     *
     * @param chatId идентификатор чата
     * @param userId идентификатор пользователя
     * @param role новая роль
     */
    @PatchMapping("/{chatId}/{userId}/role")
    @Operation(summary = "Обновить роль участника", description = "Обновляет роль участника чата")
    public void updateParticipantRole(@PathVariable UUID chatId, @PathVariable UUID userId, @RequestParam ParticipantRole role) {
        chatParticipantService.updateParticipantRole(chatId, userId, role);
    }

    /**
     * Удаление участника из чата.
     *
     * @param chatId идентификатор чата
     * @param userId идентификатор участника
     */
    @DeleteMapping("/{chatId}/{userId}")
    @Operation(summary = "Удалить участника из чата", description = "Удаляет участника из указанного чата")
    public void removeParticipant(@PathVariable UUID chatId, @PathVariable UUID userId) {
        UUID initiatorId = userService.getCurrentId().orElseThrow(
                () -> new IllegalStateException("Произошла ошибка при получении идентификатора пользователя")
        );
        chatParticipantService.removeParticipant(chatId, userId);
        messagingTemplate.convertAndSend("/topic/chat/" + chatId,
                new ParticipantRemovedEvent(chatId, userId, initiatorId));
    }

    /**
     * Проверка роли администратора.
     *
     * @param chatId идентификатор чата
     * @param userId идентификатор пользователя
     * @return признак администратора
     */
    @Operation(summary = "Проверить администратора", description = "Проверяет, является ли пользователь администратором указанного чата")
    @GetMapping("/{chatId}/{userId}/is-admin")
    public boolean isAdmin(@PathVariable UUID chatId, @PathVariable UUID userId) {
        return chatParticipantService.isAdmin(chatId, userId);
    }
}

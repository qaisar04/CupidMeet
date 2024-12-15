package com.cupidmeet.chatservice.controller.rest;

import com.cupidmeet.chatservice.domain.dto.ChatParticipantAddRequest;
import com.cupidmeet.chatservice.domain.dto.ChatParticipantResponse;
import com.cupidmeet.chatservice.domain.enumeration.ParticipantRole;
import com.cupidmeet.chatservice.service.ChatParticipantService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/chat-participants")
public class ChatParticipantController {

    private final ChatParticipantService chatParticipantService;

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
     * Добавление участника в чат.
     *
     * @param chatId идентификатор чата
     * @param request запрос на добавление участника
     * @return данные добавленного участника
     */
    @PostMapping("/{chatId}")
    @Operation(summary = "Добавить участника в чат", description = "Добавляет нового участника в указанный чат")
    public ChatParticipantResponse addParticipant(@PathVariable UUID chatId, @RequestBody ChatParticipantAddRequest request) {
        return chatParticipantService.addParticipant(chatId, request);
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
        chatParticipantService.removeParticipant(chatId, userId);
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

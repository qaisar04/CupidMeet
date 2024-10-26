package com.cupidmeet.chatservice.controller;

import com.cupidmeet.chatservice.domain.dto.CreateChatRequest;
import com.cupidmeet.chatservice.domain.entity.Chat;
import com.cupidmeet.chatservice.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RestController("/v1/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    @Operation(operationId = "createChat", summary = "Создание чата")
    public ResponseEntity<Chat> create(@RequestBody CreateChatRequest request) {
        return ResponseEntity.ok(chatService.create(request));
    }

    @GetMapping("/participant/{participantId}")
    @Operation(operationId = "getChatsByParticipant", summary = "Получение чатов по идентификатору пользователя")
    public ResponseEntity<Set<Chat>> getChatsByParticipant(@PathVariable UUID participantId) {
        return ResponseEntity.ok(chatService.getChatsByParticipant(participantId));
    }
}

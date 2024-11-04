package com.cupidmeet.chatservice.controller;

import com.cupidmeet.chatservice.domain.dto.CreateMessageRequest;
import com.cupidmeet.chatservice.domain.dto.Notification;
import com.cupidmeet.chatservice.domain.entity.Message;
import com.cupidmeet.chatservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message")
    void sendMessage(@Payload CreateMessageRequest request) {
        Message message = messageService.saveMessage(request);
        messagingTemplate.convertAndSendToUser(
                message.getChat().getId().toString(),
                "/queue/messages",
                Notification.builder()
                        .id(message.getId())
                        .senderId(message.getUserId())
                        .chatId(message.getChat().getId())
                        .content(message.getContent())
                        .build()
        );
    }

    @GetMapping("/messages/{{chatId}")
    public ResponseEntity<List<Message>> getChatMessages(
            @PathVariable("chatId") UUID chatId
    ) {
        return ResponseEntity.ok(messageService.getChatMessages(chatId));
    }
}

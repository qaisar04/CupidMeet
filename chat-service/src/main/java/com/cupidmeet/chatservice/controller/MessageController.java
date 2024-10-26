package com.cupidmeet.chatservice.controller;

import com.cupidmeet.chatservice.domain.dto.CreateMessageRequest;
import com.cupidmeet.chatservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat-message")
    void sendMessage(@Payload CreateMessageRequest request) {
        messageService.saveMessage(request);
        messagingTemplate.convertAndSend("/topic/chat/" + request.getChatId(), request);
    }
}

package com.cupidmeet.chatservice.controller.websocket;

import com.cupidmeet.chatservice.domain.dto.MessageResponse;
import com.cupidmeet.chatservice.domain.dto.MessageSendRequest;
import com.cupidmeet.chatservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageWebSocketController {

    private final MessageService messageService;

    /**
     * Отправка сообщения.
     *
     * @param request запрос на отправку сообщения
     * @return отправленное сообщение
     */
    @MessageMapping("/chat/{chatId}/send")
    @SendTo("/topic/chat/{chatId}")
    public MessageResponse sendMessage(MessageSendRequest request) {
        return messageService.sendMessage(request);
    }
}

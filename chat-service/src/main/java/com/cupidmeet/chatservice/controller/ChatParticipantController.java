package com.cupidmeet.chatservice.controller;

import com.cupidmeet.chatservice.domain.entity.ChatParticipant;
import com.cupidmeet.chatservice.service.ChatParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatParticipantController {

    private final ChatParticipantService chatParticipantService;

    @SendTo("/user/topic")
    @MessageMapping("/user.add")
    public ChatParticipant add(@Payload ChatParticipant chatParticipant) {
        chatParticipantService.save(chatParticipant);
        return chatParticipant;
    }

    @SendTo("/user/topic")
    @MessageMapping("/user.disconnect")
    public ChatParticipant disconnect(@Payload ChatParticipant chatParticipant) {
        chatParticipantService.disconnect(chatParticipant.getId());
        return chatParticipant;
    }
}

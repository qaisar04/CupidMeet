package com.cupidmeet.chatservice.service.impl;

import com.cupidmeet.chatservice.domain.dto.CreateMessageRequest;
import com.cupidmeet.chatservice.domain.entity.Message;
import com.cupidmeet.chatservice.mapper.MessageMapper;
import com.cupidmeet.chatservice.repository.MessageRepository;
import com.cupidmeet.chatservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    @Override
    public Message saveMessage(CreateMessageRequest messageRequest) {
        Message message = messageMapper.toEntity(messageRequest);
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getChatMessages(UUID chatId) {
        return messageRepository.findAllByChatId(chatId);
    }
}

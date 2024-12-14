package com.cupidmeet.chatservice.service.impl;

import com.cupidmeet.chatservice.domain.dto.ChatParticipantAddRequest;
import com.cupidmeet.chatservice.domain.dto.ChatParticipantResponse;
import com.cupidmeet.chatservice.domain.entity.Chat;
import com.cupidmeet.chatservice.domain.entity.ChatParticipant;
import com.cupidmeet.chatservice.domain.enumeration.ParticipantRole;
import com.cupidmeet.chatservice.mapper.ChatParticipantMapper;
import com.cupidmeet.chatservice.repository.ChatParticipantRepository;
import com.cupidmeet.chatservice.repository.ChatRepository;
import com.cupidmeet.chatservice.service.ChatParticipantService;
import com.cupidmeet.runtimecore.exception.CustomRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.cupidmeet.chatservice.message.Messages.CHAT_NOT_ALLOWED;
import static com.cupidmeet.chatservice.message.Messages.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ChatParticipantServiceImpl implements ChatParticipantService {

    private final ChatRepository chatRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatParticipantMapper chatParticipantMapper;

    @Override
    public List<ChatParticipantResponse> getParticipants(UUID chatId) {
        return getChatById(chatId).getParticipants().stream()
                .map(chatParticipantMapper::toResponse)
                .toList();
    }

    @Override
    public ChatParticipantResponse addParticipant(UUID chatId, ChatParticipantAddRequest request) {
        Chat chat = getChatById(chatId);
        if (chat.getParticipants().stream().anyMatch(p -> p.getUserId().equals(request.getUserId()))) {
            throw new CustomRuntimeException(CHAT_NOT_ALLOWED, "Пользователь уже является участником чата");
        }

        ChatParticipant participant = ChatParticipant.builder()
                .chat(chat)
                .userId(request.getUserId())
                .build();

        chatParticipantRepository.save(participant);
        return chatParticipantMapper.toResponse(participant);
    }

    @Override
    public void updateParticipantRole(UUID chatId, UUID userId, ParticipantRole role) {
        ChatParticipant participant = chatParticipantRepository.findByChatIdAndUserId(chatId, userId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND, "Участник", "в чате", userId));

        participant.setRole(role);
        chatParticipantRepository.save(participant);
    }

    @Override
    public void removeParticipant(UUID chatId, UUID userId) {
        ChatParticipant participant = chatParticipantRepository.findByChatIdAndUserId(chatId, userId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND, "Участник", "в чате", userId));

        chatParticipantRepository.delete(participant);
    }

    @Override
    public boolean isAdmin(UUID chatId, UUID userId) {
        return chatParticipantRepository.findByChatIdAndUserId(chatId, userId)
                .map(p -> p.getRole() == ParticipantRole.ADMIN || p.getRole() == ParticipantRole.OWNER)
                .orElse(false);
    }

    private Chat getChatById(UUID chatId) {
        return chatRepository.findById(chatId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND, "Чат", "идентификатором", chatId));
    }
}

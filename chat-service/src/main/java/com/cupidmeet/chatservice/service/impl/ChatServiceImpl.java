package com.cupidmeet.chatservice.service.impl;

import com.cupidmeet.chatservice.client.UserDetailsClient;
import com.cupidmeet.chatservice.domain.dto.CreateChatRequest;
import com.cupidmeet.chatservice.domain.dto.UserResponse;
import com.cupidmeet.chatservice.domain.entity.Chat;
import com.cupidmeet.chatservice.domain.entity.ChatParticipant;
import com.cupidmeet.chatservice.domain.enumeration.ChatType;
import com.cupidmeet.chatservice.domain.enumeration.ParticipantRole;
import com.cupidmeet.chatservice.mapper.ChatMapper;
import com.cupidmeet.chatservice.repository.ChatRepository;
import com.cupidmeet.chatservice.service.ChatService;
import com.cupidmeet.chatservice.service.UserService;
import com.cupidmeet.commonmessage.exception.CommonRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.cupidmeet.chatservice.message.Messages.*;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatMapper converter;
    private final UserDetailsClient userDetailsClient;
    private final UserService userService;

    @Override
    public Chat create(CreateChatRequest request) {
        Chat chat = converter.toEntity(request);

        if (chat == null || chat.getChatType() == null) {
            throw new CommonRuntimeException(CHAT_TYPE_IS_NULL);
        }

        if (chat.getParticipants() == null || chat.getParticipants().isEmpty()) {
            throw new CommonRuntimeException(
                    CHAT_NOT_ALLOWED, "необходимо указать хотя бы одного участника"
            );
        }

        // Проверка перед созданием чата
        if (chat.getChatType().equals(ChatType.PRIVATE)) {
            if (chat.getParticipants().size() >= 2) {
                throw new CommonRuntimeException(
                        CHAT_NOT_ALLOWED, "в приватном чате должно быть не больше двух участников"
                );
            }
        } else {
            if (chat.getParticipants().size() < 2) {
                throw new CommonRuntimeException(
                        CHAT_NOT_ALLOWED, "в приватном чате должно быть не больше двух участников"
                );
            }
        }

        Set<UUID> participantIds = chat.getParticipants().stream()
                .map(ChatParticipant::getId)
                .collect(Collectors.toSet());

        ResponseEntity<Map<UUID, UserResponse>> participantMap = userDetailsClient.get(participantIds);
        if (participantMap.getStatusCode() != HttpStatus.OK) {
            throw new CommonRuntimeException(CHAT_NOT_ALLOWED, "Ошибка при получении информации о участниках");
        }

        if (participantMap.getBody() == null || participantMap.getBody().size() != participantIds.size()) {
            throw new CommonRuntimeException(CHAT_NOT_ALLOWED, "не удалось получить информацию о всех участниках");
        }

        UUID currentUserId = userService.getCurrentId().orElseThrow(() ->
                new CommonRuntimeException(CHAT_NOT_ALLOWED, "не удалось получить информацию о текущем пользователе")
        );

        Set<ChatParticipant> chatParticipants = request.getParticipants().stream()
                .map(participantId -> {
                    ChatParticipant participant = ChatParticipant.builder()
                            .chat(chat)
                            .userId(participantId)
                            .role(ParticipantRole.MEMBER)
                            .build();

                    if (participantId.equals(currentUserId)) {
                        participant.setRole(ParticipantRole.OWNER);
                    }

                    return participant;
                }).collect(Collectors.toSet());

        chat.setParticipants(chatParticipants);
        return chatRepository.save(chat);
    }

    @Override
    @Cacheable(value = "chat", key = "#participantId")
    public Set<Chat> getChatsByParticipant(UUID participantId) {
        return chatRepository.findByParticipantsId(participantId);
    }

    @Override
    @Cacheable(value = "chatParticipant", key = "#chatId")
    public Set<ChatParticipant> getChatParticipants(UUID chatId) {
        Chat chat = get(chatId);
        return chat.getParticipants();
    }

    private Chat get(UUID chatId) {
        return chatRepository.findById(chatId)
                .orElseThrow(() -> new CommonRuntimeException(NOT_FOUND, "Чат", "идентификатором", chatId));
    }
}

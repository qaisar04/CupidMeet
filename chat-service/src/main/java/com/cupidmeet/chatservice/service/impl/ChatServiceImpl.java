package com.cupidmeet.chatservice.service.impl;

import com.cupidmeet.chatservice.client.UserDetailsClient;
import com.cupidmeet.chatservice.domain.dto.ChatAddParticipantsRequest;
import com.cupidmeet.chatservice.domain.dto.ChatCreateRequest;
import com.cupidmeet.chatservice.domain.dto.ChatResponse;
import com.cupidmeet.chatservice.domain.dto.UserResponse;
import com.cupidmeet.chatservice.domain.entity.Chat;
import com.cupidmeet.chatservice.domain.entity.ChatParticipant;
import com.cupidmeet.chatservice.domain.enumeration.ChatType;
import com.cupidmeet.chatservice.domain.enumeration.ParticipantRole;
import com.cupidmeet.chatservice.domain.enumeration.ParticipantStatus;
import com.cupidmeet.chatservice.mapper.ChatMapper;
import com.cupidmeet.chatservice.repository.ChatRepository;
import com.cupidmeet.chatservice.service.ChatService;
import com.cupidmeet.chatservice.service.UserService;
import com.cupidmeet.runtimecore.exception.CustomRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    private final ChatMapper chatMapper;

    @Override
    public ChatResponse createChat(ChatCreateRequest request) {
        Chat chat = converter.toEntity(request);

        if (chat == null || chat.getChatType() == null) {
            throw new CustomRuntimeException(CHAT_TYPE_IS_NULL);
        }

        if (CollectionUtils.isEmpty(request.getParticipants())) {
            throw new CustomRuntimeException(
                    CHAT_NOT_ALLOWED, "необходимо указать хотя бы одного участника"
            );
        }

        // Проверка перед созданием чата
        if (chat.getChatType().equals(ChatType.PRIVATE)) {
            if (chat.getParticipants().size() >= 2) {
                throw new CustomRuntimeException(
                        CHAT_NOT_ALLOWED, "в приватном чате должно быть не больше двух участников"
                );
            }
        } else {
            if (chat.getParticipants().size() < 2) {
                throw new CustomRuntimeException(
                        CHAT_NOT_ALLOWED, "в приватном чате должно быть не больше двух участников"
                );
            }
        }

        Set<UUID> participantIds = chat.getParticipants().stream()
                .map(ChatParticipant::getId)
                .collect(Collectors.toSet());

        ResponseEntity<Map<UUID, UserResponse>> participantMap = userDetailsClient.get(participantIds);
        if (participantMap.getStatusCode() != HttpStatus.OK) {
            throw new CustomRuntimeException(CHAT_NOT_ALLOWED, "Ошибка при получении информации о участниках");
        }

        if (participantMap.getBody() == null || participantMap.getBody().size() != participantIds.size()) {
            throw new CustomRuntimeException(CHAT_NOT_ALLOWED, "не удалось получить информацию о всех участниках");
        }

        UUID currentUserId = userService.getCurrentId().orElseThrow(() ->
                new CustomRuntimeException(CHAT_NOT_ALLOWED, "не удалось получить информацию о текущем пользователе")
        );

        Set<ChatParticipant> chatParticipants = request.getParticipants().stream()
                .map(participantId -> {
                    ChatParticipant participant = ChatParticipant.builder()
                            .chat(chat)
                            .userId(participantId)
                            .role(ParticipantRole.MEMBER)
                            .status(ParticipantStatus.ONLINE)
                            .build();

                    if (participantId.equals(currentUserId)) {
                        participant.setRole(ParticipantRole.OWNER);
                    }

                    return participant;
                }).collect(Collectors.toSet());

        chat.setParticipants(chatParticipants);
        return chatMapper.toResponse(chatRepository.save(chat));
    }

    @Override
    public ChatResponse getChatById(UUID chatId) {
        return chatMapper.toResponse(get(chatId));
    }

    @Override
    public Page<ChatResponse> getUserChats(UUID userId, Pageable pageable) {
        if (userId == null) {
            throw new CustomRuntimeException(VALIDATION_ERROR, "userId", "не может быть null");
        }

        // TODO: Чат может быть приватным, в таком случае генерируем название на основе другого участника

        return chatRepository.findByParticipants_UserId(userId, pageable)
                .map(chatMapper::toResponse);
    }

    @Override
    public void deleteChat(UUID chatId) {
        if (!chatRepository.existsById(chatId)) {
            throw new CustomRuntimeException(NOT_FOUND, "Чат", "идентификатором", chatId);
        }
        chatRepository.deleteById(chatId);
    }

    @Override
    public void addParticipants(UUID chatId, ChatAddParticipantsRequest request) {
        if (CollectionUtils.isEmpty(request.getUserIds())) {
            throw new CustomRuntimeException(VALIDATION_ERROR, "userIds", "не может быть пустым");
        }

        Chat chat = get(chatId);

        request.getUserIds().forEach(userId -> {
            if (chat.getParticipants().stream().anyMatch(p -> p.getUserId().equals(userId))) {
                throw new CustomRuntimeException(CHAT_NOT_ALLOWED, "Пользователь уже является участником чата");
            }
            ChatParticipant participant = ChatParticipant.builder()
                    .chat(chat)
                    .userId(userId)
                    .role(ParticipantRole.MEMBER)
                    .status(ParticipantStatus.OFFLINE)
                    .build();
            chat.getParticipants().add(participant);
        });

        chatRepository.save(chat);
    }

    @Override
    public void removeParticipant(UUID chatId, UUID userId) {
        Chat chat = get(chatId);
        ChatParticipant participant = chat.getParticipants().stream()
                .filter(p -> p.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND, "Участник", "в чате", userId));

        chat.getParticipants().remove(participant);
        chatRepository.save(chat);
    }

    @Override
    public boolean isUserInChat(UUID chatId, UUID userId) {
        if (chatId == null || userId == null) {
            throw new CustomRuntimeException(VALIDATION_ERROR, "chatId/userId", "не могут быть null");
        }
        return chatRepository.existsById(chatId) &&
               chatRepository.findById(chatId)
                       .map(chat -> chat.getParticipants().stream().anyMatch(p -> p.getUserId().equals(userId)))
                       .orElse(false);
    }

    private Chat get(UUID chatId) {
        return chatRepository.findById(chatId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND, "Чат", "идентификатором", chatId));
    }
}

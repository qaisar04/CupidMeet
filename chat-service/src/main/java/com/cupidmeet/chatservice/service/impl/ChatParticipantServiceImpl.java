package com.cupidmeet.chatservice.service.impl;

import com.cupidmeet.chatservice.domain.entity.ChatParticipant;
import com.cupidmeet.chatservice.domain.enumeration.ParticipantStatus;
import com.cupidmeet.chatservice.repository.ChatParticipantRepository;
import com.cupidmeet.chatservice.service.ChatParticipantService;
import com.cupidmeet.commonmessage.exception.CommonRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.cupidmeet.chatservice.message.Messages.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ChatParticipantServiceImpl implements ChatParticipantService {

    private final ChatParticipantRepository chatParticipantRepository;

    @Override
    public void save(ChatParticipant chatParticipant) {
        chatParticipant.setStatus(ParticipantStatus.ONLINE);
        chatParticipantRepository.save(chatParticipant);
    }

    @Override
    public void disconnect(UUID participantId) {
        ChatParticipant chatParticipant = get(participantId);
        chatParticipant.setStatus(ParticipantStatus.OFFLINE);
        chatParticipantRepository.save(chatParticipant);
    }

    private ChatParticipant get(UUID participantId) {
        return chatParticipantRepository.findById(participantId).orElseThrow(
                () -> new CommonRuntimeException(NOT_FOUND)
        );
    }
}

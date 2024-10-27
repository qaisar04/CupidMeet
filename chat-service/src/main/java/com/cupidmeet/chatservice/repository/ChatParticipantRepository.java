package com.cupidmeet.chatservice.repository;

import com.cupidmeet.chatservice.domain.entity.ChatParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, UUID> {

}

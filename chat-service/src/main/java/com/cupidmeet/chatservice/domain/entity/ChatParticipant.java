package com.cupidmeet.chatservice.domain.entity;

import com.cupidmeet.chatservice.domain.enumeration.ParticipantRole;
import com.cupidmeet.chatservice.domain.enumeration.ParticipantStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Участник чата.
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_participants")
@EqualsAndHashCode(of = "id")
public class ChatParticipant {

    /**
     * Уникальный идентификатор сущности.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Чат.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private Chat chat;

    /**
     * Идентификатор пользователя отправителя.
     */
    private UUID userId;

    /**
     * Статус участника.
     */
    @Enumerated(EnumType.STRING)
    private ParticipantStatus status;

    /**
     * Роль участника чата.
     */
    @Enumerated(EnumType.STRING)
    private ParticipantRole role;

    /**
     * Дата присоединения к чату.
     */
    @CreationTimestamp
    private ZonedDateTime joinedAt;
}

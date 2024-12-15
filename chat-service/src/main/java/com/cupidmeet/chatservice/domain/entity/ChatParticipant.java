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
     * Уникальный идентификатор участника.
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
     * Идентификатор пользователя отправителя. (то что из внешней системы)
     */
    private UUID userId;

    /**
     * Статус участника.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ParticipantStatus status;

    /**
     * Роль участника чата.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ParticipantRole role;

    /**
     * Дата присоединения к чату.
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private ZonedDateTime joinedAt;
}

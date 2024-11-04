package com.cupidmeet.chatservice.domain.entity;

import com.cupidmeet.chatservice.domain.enumeration.MessageStatus;
import com.cupidmeet.chatservice.domain.enumeration.MessageType;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Сообщение.
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages")
@EqualsAndHashCode(of = "id")
public class Message {

    /**
     * Уникальный идентификатор сущности.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Чат к которому относится сообщение.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Chat chat;

    /**
     * Идентификатор пользователя отправителя.
     */
    private UUID userId;

    /**
     * Текст сообщения.
     */
    private String content;

    /**
     * Временная метка сообщения.
     */
    private ZonedDateTime timestamp;

    /**
     * Стаус сообщения.
     */
    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    /**
     * Тип сообщения.
     */
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @PrePersist
    protected void onCreate() {
        status = MessageStatus.SENT;
        timestamp = ZonedDateTime.now();
    }
}

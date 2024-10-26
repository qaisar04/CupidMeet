package com.cupidmeet.chatservice.domain.entity;

import com.cupidmeet.chatservice.domain.enumeration.ChatType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Чат.
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chats")
@EqualsAndHashCode(of = "id")
public class Chat {

    /**
     * Уникальный идентификатор сущности.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Тип чата.
     */
    @Enumerated(EnumType.STRING)
    private ChatType chatType;

    /**
     * Дата и время создания чата.
     */
    @CreationTimestamp
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    /**
     * Название чата.
     */
    private String name;

    /**
     * Список участников чата.
     */
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChatParticipant> participants;
}

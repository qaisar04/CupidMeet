package com.cupidmeet.chatservice.domain.entity;

import com.cupidmeet.chatservice.domain.enumeration.ChatType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
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
     * Тип чата (например, приватный, групповой).
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
     * Название чата (опционально для приватных чатов).
     */
    private String name;

    /**
     * Список участников чата.
     */
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChatParticipant> participants;

    /**
     * Возвращает динамическое имя для личных чатов на основе другого участника.
     *
     * @param currentUserId Идентификатор текущего пользователя.
     * @return Имя другого участника или "Неизвестный пользователь", если участник не найден.
     */
    public String getDynamicName(UUID currentUserId) {
        return participants.stream()
                .filter(participant -> !participant.getUserId().equals(currentUserId))
                .findFirst()
                .map(participant -> fetchUserName(participant.getUserId()))
                .orElse("Неизвестный пользователь");
    }

    private String fetchUserName(UUID userId) {
        // TODO: Заменить на реальный вызов UserService для получения имени.
        return "Пользователь_" + userId.toString().substring(0, 8);
    }
}

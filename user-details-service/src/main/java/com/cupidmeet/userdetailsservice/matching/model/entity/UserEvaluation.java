package com.cupidmeet.userdetailsservice.matching.model.entity;

import com.cupidmeet.userdetailsservice.matching.model.types.EvaluationStatus;
import com.cupidmeet.userdetailsservice.matching.model.types.ReactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

/**
 * Сущность, представляющая оценки пользователей.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_evaluation")
public class UserEvaluation {

    /**
     * Уникальный идентификатор оценки.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Идентификатор пользователя, который оценивает.
     */
    @Column(name = "user_id")
    private UUID userId;

    /**
     * Идентификатор оцененного пользователя.
     */
    @Column(name = "rated_user_id")
    private UUID ratedUserId;

    /**
     * Время и дата, когда была произведена оценка.
     */
    @CreationTimestamp
    @Column(name = "evaluation_at")
    private Instant evaluationAt;

    /**
     * Тип реакции, которую оставил пользователь при оценке пользователя.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "reaction_type")
    private ReactionType reactionType;

    /**
     * Статус оценки.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "evaluation_status")
    private EvaluationStatus status;

    /**
     * Сообщение для лайка.
     */
    @Column(name = "message")
    private String message;
}
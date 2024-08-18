package com.cupidmeet.feedbackservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import com.cupidmeet.feedbackservice.domain.type.Grade;

import java.util.UUID;


/**
 * Сущность, представляющая отзыв пользователя.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "feedback")
public class Feedback extends BaseEntity {

    /**
     * Идентификатор пользователя, который оставил отзыв.
     */
    private UUID userId;

    /**
     * Оценка, выставленная пользователем, должна быть в пределах от 1 до 5.
     */
    @Enumerated(EnumType.STRING)
    private Grade grade;

    /**
     * Комментарий, оставленный пользователем.
     */
    private String comment;
}
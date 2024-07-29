package ru.polskiy.feedbackservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.polskiy.feedbackservice.model.type.Grade;


/**
 * Entity class representing user's feedback.
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "feedback", schema = "feedback")
public class Feedback extends BaseEntity {

    /**
     * ID of the user who provided the feedback.
     */
    private Long userId;

    /**
     * Grade provided by the user, must be between 1 and 5.
     */
    @Enumerated(EnumType.STRING)
    private Grade grade;

    /**
     * Comment provided by the user.
     */
    private String comment;
}
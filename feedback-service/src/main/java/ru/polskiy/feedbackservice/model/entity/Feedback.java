package ru.polskiy.feedbackservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Entity class representing user's feedback.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feedback")
public class Feedback extends BaseEntity {

    /**
     * ID of the user who provided the feedback.
     */
    private Long userId;

    /**
     * Grade provided by the user, must be between 1 and 5.
     */
    private Byte grade;

    /**
     * Comment provided by the user.
     */
    private String comment;
}
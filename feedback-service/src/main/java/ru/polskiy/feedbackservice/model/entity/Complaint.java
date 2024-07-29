package ru.polskiy.feedbackservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.polskiy.feedbackservice.model.type.ComplaintType;
import ru.polskiy.feedbackservice.model.type.Status;

/**
 * Entity class representing a user's complaint.
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "complaint", schema = "feedback")
public class Complaint extends BaseEntity {

    /**
     * ID of the user who send the complaint.
     */
    private Long fromUserId;

    /**
     * ID of the user against whom the complaint.
     */
    private Long toUserId;

    /**
     * Comment provided by the user.
     */
    private String comment;

    /**
     * Status of the complaint.
     */
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * Type of the complaint.
     * Filling by user
     */
    @Enumerated(EnumType.STRING)
    private ComplaintType complaintType;
}
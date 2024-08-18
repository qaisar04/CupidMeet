package com.cupidmeet.feedbackservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import com.cupidmeet.feedbackservice.domain.type.ComplaintType;
import com.cupidmeet.feedbackservice.domain.type.Status;

import java.util.UUID;

/**
 * Сущность, представляющая жалобу в системе.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "complaint")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Complaint extends BaseEntity {

    /**
     * Идентификатор пользователя, который подал жалобу.
     */
    private UUID fromUserId;

    /**
     * Идентификатор пользователя, на которого подана жалоба.
     */
    private UUID toUserId;

    /**
     * Комментарий, связанный с жалобой.
     */
    private String comment;

    /**
     * Статус жалобы.
     */
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * Тип жалобы.
     */
    @Enumerated(EnumType.STRING)
    private ComplaintType complaintType;
}
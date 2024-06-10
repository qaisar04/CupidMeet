package ru.polskiy.feedbackservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.polskiy.feedbackservice.model.type.ComplaintType;
import ru.polskiy.feedbackservice.model.type.Status;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "complaint")
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    private String username;

    @NotBlank
    private String comment;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "complaint_type")
    @Enumerated(EnumType.STRING)
    private ComplaintType complaintType;
}

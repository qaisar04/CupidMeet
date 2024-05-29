package ru.polskiy.feedbackservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.polskiy.feedbackservice.model.type.Status;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "complain")
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Email
    private String username;

    @NotBlank
    @NotEmpty
    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
}

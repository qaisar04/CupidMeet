package ru.polskiy.feedbackservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.polskiy.feedbackservice.model.type.CommentType;
import ru.polskiy.feedbackservice.model.type.Status;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feed_back")
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank
    @NotEmpty
    @Column(nullable = false)
    private String comment;

    @Column(name = "comment_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CommentType commentType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
}

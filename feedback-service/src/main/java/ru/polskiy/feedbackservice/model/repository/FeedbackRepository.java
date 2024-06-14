package ru.polskiy.feedbackservice.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.polskiy.feedbackservice.model.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}

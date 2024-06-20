package ru.polskiy.feedbackservice.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.polskiy.feedbackservice.model.entity.Feedback;

/**
 * Repository for managing Complaint entities.
 */
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    /**
     * Retrieves feedback based on the user ID.
     *
     * @param userId The ID of the user for whom feedback is being retrieved.
     * @return The Feedback object associated with the given user ID, or null if not found.
     */
    Feedback findByUserId(Long userId);
}

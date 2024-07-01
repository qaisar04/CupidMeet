package ru.polskiy.feedbackservice.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
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

    /**
     * Checks if a feedback exists for the given userId.
     *
     * @param userId The ID of the user for whom to check feedback existence
     * @return true if a feedback exists for the user and false otherwise
     */
    Boolean existsByUserId(Long userId);

    boolean existsById(Long id);

    /**
     * Updates the comment, grade, and updatedAt fields of a Feedback entity with the specified id.
     *
     * @param comment The new comment to update in the Feedback entity
     * @param grade   The new grade to update in the Feedback entity
     * @param id      The ID of the Feedback entity to update
     */
    @Modifying
    @Transactional
    @Query("UPDATE Feedback u SET u.comment = :comment,u.grade= :grade, u.updatedAt = CURRENT_TIMESTAMP WHERE u.id = :id")
    void updateFeedback(String comment, Byte grade, Long id);
}

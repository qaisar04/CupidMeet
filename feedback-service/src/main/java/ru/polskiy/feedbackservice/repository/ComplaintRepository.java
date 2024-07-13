package ru.polskiy.feedbackservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.polskiy.feedbackservice.model.entity.Complaint;


/**
 * Repository for managing Complaint entities.
 */
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    /**
     * Finds a {@link Complaint} entity based on the provided fromUserId and toUserId.
     *
     * @param fromUserId The ID of the user who filed the complaint.
     * @param toUserId   The ID of the user who is the subject of the complaint.
     * @return The {@link Complaint} entity if found, otherwise null.
     */
    Boolean existsByFromUserIdAndToUserId(Long fromUserId, Long toUserId);
}
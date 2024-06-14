package ru.polskiy.feedbackservice.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.polskiy.feedbackservice.model.entity.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
}

package ru.polskiy.feedbackservice.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.polskiy.feedbackservice.model.entity.Review;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}

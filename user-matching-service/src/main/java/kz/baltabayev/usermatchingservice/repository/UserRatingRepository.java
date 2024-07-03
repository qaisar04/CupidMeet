package kz.baltabayev.usermatchingservice.repository;

import kz.baltabayev.usermatchingservice.model.entity.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRatingRepository extends JpaRepository<UserRating, Long> {

    Optional<UserRating> findByUserId(Long userId);
}
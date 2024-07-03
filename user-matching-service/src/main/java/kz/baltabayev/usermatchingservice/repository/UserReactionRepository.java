package kz.baltabayev.usermatchingservice.repository;

import kz.baltabayev.usermatchingservice.model.entity.UserReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserReactionRepository extends JpaRepository<UserReaction, Long> {

    Optional<UserReaction> findByUserIdAndTargetUserId(Long userId, Long targetUserId);
}
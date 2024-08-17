package com.cupidmeet.userdetailsservice.user.repository;

import com.cupidmeet.userdetailsservice.user.domain.entity.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserPreferenceRepository extends JpaRepository<UserPreference, UUID> {

    boolean existsByUserId(UUID userId);

    Optional<UserPreference> findByUserId(UUID userId);
}
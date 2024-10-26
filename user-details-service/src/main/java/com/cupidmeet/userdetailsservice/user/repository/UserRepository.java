package com.cupidmeet.userdetailsservice.user.repository;

import com.cupidmeet.userdetailsservice.user.domain.entity.User;
import com.cupidmeet.userdetailsservice.user.domain.types.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = :status, u.updatedAt = CURRENT_TIMESTAMP WHERE u.id = :id")
    void updateUserStatus(UUID id, Status status);

    boolean existsByUsername(String username);
}
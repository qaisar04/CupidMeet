package com.cupidmeet.userdetailsservice.user.repository;

import com.cupidmeet.userdetailsservice.user.domain.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {

    boolean existsByUserId(UUID userId);

    Optional<UserInfo> getByUserId(UUID userId);
}
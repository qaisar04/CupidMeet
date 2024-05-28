package kz.baltabayev.telegrambotservice.repository;

import kz.baltabayev.telegrambotservice.model.entity.UserState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStateRepository extends JpaRepository<UserState, Long> {
}
package kz.baltabayev.userdetailsservice.repository;

import kz.baltabayev.userdetailsservice.model.entity.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserPreferenceRepository extends JpaRepository<UserPreference, UUID> {

}
package kz.baltabayev.userdetailsservice.repository;

import kz.baltabayev.userdetailsservice.model.entity.UserInfo;
import kz.baltabayev.userdetailsservice.model.types.Gender;
import kz.baltabayev.userdetailsservice.model.types.PersonalityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {

    @Modifying
    @Transactional
    @Query(
            "UPDATE UserInfo u SET " +
            "u.name = :name," +
            "u.age = :age," +
            "u.city = :city, " +
            "u.gender = :gender," +
            "u.personalityType = :personalityType," +
            "u.bio = :bio, " +
            "u.updatedAt = CURRENT_TIMESTAMP " +
            "WHERE u.user.id = :userId"
    )
    void updateUserInfoByUserId(
            String name, Integer age, String city, Gender gender,
            PersonalityType personalityType, String bio, Long userId
    );

    boolean existsByUserId(Long userId);
}
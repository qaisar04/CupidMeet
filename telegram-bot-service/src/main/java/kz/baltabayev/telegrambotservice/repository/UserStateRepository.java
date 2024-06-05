package kz.baltabayev.telegrambotservice.repository;

import kz.baltabayev.telegrambotservice.model.entity.UserState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserStateRepository extends JpaRepository<UserState, Long> {

    @Modifying
    @Transactional
    @Query("update UserState u set u.name = :name where u.userId = :userId")
    void updateName(Long userId, String name);

    @Modifying
    @Transactional
    @Query("update UserState u set u.age = :age where u.userId = :userId")
    void updateAge(Long userId, Integer age);

    @Modifying
    @Transactional
    @Query("update UserState u set u.gender = :gender where u.userId = :userId")
    void updateGender(Long userId, String gender);

    @Modifying
    @Transactional
    @Query("update UserState u set u.city = :city where u.userId = :userId")
    void updateCity(Long userId, String city);

    @Modifying
    @Transactional
    @Query("update UserState u set u.mbti = :mbti where u.userId = :userId")
    void updateMbti(Long userId, String mbti);

    @Modifying
    @Transactional
    @Query("update UserState u set u.about = :about where u.userId = :userId")
    void updateBio(Long userId, String about);
}
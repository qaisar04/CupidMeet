package kz.baltabayev.usermatchingservice.service;

import java.util.List;

public interface UserRatingService {

    void addRatedId(Long userId, Long targetUserId);

    List<Long> findRatedUserIds(Long userId);
}
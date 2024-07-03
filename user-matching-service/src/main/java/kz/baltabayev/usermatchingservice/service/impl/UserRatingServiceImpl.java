package kz.baltabayev.usermatchingservice.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import kz.baltabayev.usermatchingservice.model.entity.UserRating;
import kz.baltabayev.usermatchingservice.repository.UserRatingRepository;
import kz.baltabayev.usermatchingservice.service.UserRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRatingServiceImpl implements UserRatingService {

    private final UserRatingRepository userRatingRepository;

    public static final String NOT_FOUND_USER_REACTION_MESSAGE = "Not found UserRating with userId: ";

    @Override
    @Transactional
    public void addRatedId(Long userId, Long targetUserId) {
        UserRating userRating = getByUserId(userId);
        userRating.getRatedUserIds().add(targetUserId);
        userRatingRepository.save(userRating);
    }

    @Override
    public List<Long> findRatedUserIds(Long userId) {
        UserRating userRating = getByUserId(userId);
        return userRating.getRatedUserIds();
    }

    @PostConstruct
    public void init() {
        UserRating userRating = new UserRating();
        userRating.setUserId(697119914L);
        userRatingRepository.save(userRating);
    }

    private UserRating getByUserId(Long userId) {
        return userRatingRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException(NOT_FOUND_USER_REACTION_MESSAGE + userId));
    }
}
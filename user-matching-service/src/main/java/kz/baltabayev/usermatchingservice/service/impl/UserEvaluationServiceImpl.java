package kz.baltabayev.usermatchingservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.baltabayev.usermatchingservice.model.entity.UserEvaluation;
import kz.baltabayev.usermatchingservice.repository.UserEvaluationRepository;
import kz.baltabayev.usermatchingservice.service.UserEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserEvaluationServiceImpl implements UserEvaluationService {

    private final UserEvaluationRepository userEvaluationRepository;

    public static final String NOT_FOUND_USER_REACTION_MESSAGE = "Not found UserEvaluation with userId: ";

    @Override
    @Transactional
    public void addRatedId(Long userId, Long targetUserId) {
        UserEvaluation userEvaluation = getByUserId(userId);
        userEvaluation.getRatedUserIds().add(targetUserId);
        userEvaluationRepository.save(userEvaluation);
    }

    @Override
    public List<Long> findRatedUserIds(Long userId) {
        UserEvaluation userRating = getByUserId(userId);
        return userRating.getRatedUserIds();
    }

    public void create(Long userId) {
        if (!checkIfUserExists(userId)) {
            UserEvaluation evaluation = new UserEvaluation(userId);
            userEvaluationRepository.save(evaluation);
        }
    }

    private UserEvaluation getByUserId(Long userId) {
        return userEvaluationRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException(NOT_FOUND_USER_REACTION_MESSAGE + userId));
    }

    private boolean checkIfUserExists(Long userId) {
        return userEvaluationRepository.existsByUserId(userId);
    }
}
package com.cupidmeet.userdetailsservice.matching.scheduler;

import com.cupidmeet.userdetailsservice.matching.model.entity.UserEvaluation;
import com.cupidmeet.userdetailsservice.matching.model.types.EvaluationStatus;
import com.cupidmeet.userdetailsservice.matching.repository.UserEvaluationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Сервис для очистки устаревших записей оценок пользователей.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "application.user.history.cleanup.scheduler.enabled", havingValue = "true")
public class UserHistoryCleanupScheduler {

    private final UserEvaluationRepository userEvaluationRepository;

    /**
     * Запуск очистки истории оценок пользователей.
     */
    @Scheduled(cron = "${application.user.history.cleanup.reprocess-delayed-cron}")
    public void resetUserEvaluationHistory() {
        log.info("Запуск очистки истории оценок пользователей.");

        Instant oneMonthAgo = Instant.now().minus(30, ChronoUnit.DAYS);
        List<UserEvaluation> evaluations = userEvaluationRepository.findByEvaluationAtBeforeAndStatus(oneMonthAgo, EvaluationStatus.ACTIVE);

        if (CollectionUtils.isEmpty(evaluations)) {
            log.info("Записи для обработки не найдены.");
            return;
        }

        try {
            evaluations.forEach(this::processEvaluation);
            log.info("Очистка истории оценок пользователей завершена успешно.");
        } catch (Exception e) {
            log.error("Ошибка при очистке истории оценок пользователей.", e);
        }
    }

    public void processEvaluation(UserEvaluation evaluation) {
        userEvaluationRepository.updateEvaluationStatus(evaluation.getId(), EvaluationStatus.OBSOLETE);
    }
}
package kz.baltabayev.storageservice.scheduler;

import kz.baltabayev.storageservice.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Компонент планировщика для очистки удалённых файлов.
 */
@Component
@RequiredArgsConstructor
public class FileCleanupScheduler {

    private final FileService fileService;

    /**
     * Периодически очищает удалённые файлы в соответствии с расписанием, определённым в конфигурации.
     * Получает идентификаторы файлов, помеченных для удаления, и отправляет данные на сервис хранения
     * для их удаления.
     */
    @Scheduled(cron = "${scheduler.cleanup.cron}")
    @Retryable(maxAttemptsExpression = "${retry.maxAttempts:5}",
            backoff = @Backoff(delayExpression = "${retry.backoffDelay:2000}"))
    public void cleanUpDeletedFiles() {
        fileService.processScheduledDeletions();
    }
}
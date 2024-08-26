package kz.baltabayev.storageservice.scheduler;

import kz.baltabayev.storageservice.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler component for cleaning up deleted files.
 */
@Component
@RequiredArgsConstructor
public class FileCleanupScheduler {

  private final FileService fileService;

  /**
   * Cleans up deleted files according to the specified schedule. Retrieves the IDs of files marked
   * for deletion from the FileAttachmentService and sends the data to the storage service via the
   * client.
   */
  @Scheduled(cron = "${scheduler.cron}")
  @Retryable(maxAttemptsExpression = "${retry.maxAttempts:5}",
      backoff = @Backoff(delayExpression = "${retry.backoffDelay:2000}"))
  public void cleanUpDeletedFiles() {
    fileService.processScheduledDeletions();
  }
}
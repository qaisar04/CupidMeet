package com.cupidmeet.userdetailsservice.user.service;

import com.cupidmeet.userdetailsservice.user.domain.entity.FileAttachment;

import java.util.UUID;

/**
 * Сервисный интерфейс для управления файловыми вложениями.
 */
public interface FileAttachmentService {

  void addAttachment(UUID ideaId, FileAttachment attachment);

  void removeAttachment(UUID attachmentId);
}
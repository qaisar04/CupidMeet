package kz.baltabayev.userdetailsservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.baltabayev.userdetailsservice.model.entity.FileAttachment;
import kz.baltabayev.userdetailsservice.model.entity.UserInfo;
import kz.baltabayev.userdetailsservice.repository.FileAttachmentRepository;
import kz.baltabayev.userdetailsservice.service.FileAttachmentService;
import kz.baltabayev.userdetailsservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of the FileAttachmentService interface for managing file attachments.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileAttachmentServiceImpl implements FileAttachmentService {

    private final UserService userService;
    private final FileAttachmentRepository fileAttachmentRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void addAttachment(Long userId, FileAttachment attachment) {
        UserInfo userInfo = userService.get(userId).getUserInfo();
        attachment.setUserInfo(userInfo);
        fileAttachmentRepository.save(attachment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void removeAttachment(UUID attachmentId) {
        FileAttachment attachment = getById(attachmentId);

        if (attachment.getDeletedAt() != null) {
            throw new EntityNotFoundException("FileAttachment with id " + attachmentId + " is already marked as deleted.");
        }

        attachment.setDeletedAt(LocalDateTime.now());
        fileAttachmentRepository.save(attachment);
    }

    /**
     * Retrieves a FileAttachment by its ID.
     *
     * @param attachmentId the UUID of the attachment to be retrieved
     * @return the FileAttachment with the specified ID
     * @throws EntityNotFoundException if the attachment with the specified ID is not found
     */
    private FileAttachment getById(UUID attachmentId) {
        return fileAttachmentRepository.findById(attachmentId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Not found FileAttachment with id: " + attachmentId)
                );
    }
}

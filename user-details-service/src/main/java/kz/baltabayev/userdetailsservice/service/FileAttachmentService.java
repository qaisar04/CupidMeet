package kz.baltabayev.userdetailsservice.service;

import kz.baltabayev.userdetailsservice.model.entity.FileAttachment;

import java.util.UUID;

/**
 * Service interface for managing file attachments associated with ideas.
 */
public interface FileAttachmentService {

    /**
     * Adds a new file attachment to the specified user.
     *
     * @param userId     the UUID of the user to which the attachment will be added
     * @param attachment the file attachment to be added
     */
    void addAttachment(Long userId, FileAttachment attachment);

    /**
     * Removes a file attachment from the user.
     *
     * @param attachmentId the UUID of the attachment to be removed
     */
    void removeAttachment(UUID attachmentId);
}
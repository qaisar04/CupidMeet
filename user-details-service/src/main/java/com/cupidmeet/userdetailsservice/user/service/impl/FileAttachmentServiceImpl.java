package com.cupidmeet.userdetailsservice.user.service.impl;

import com.cupidmeet.userdetailsservice.user.domain.entity.FileAttachment;
import com.cupidmeet.userdetailsservice.user.domain.entity.User;
import com.cupidmeet.userdetailsservice.user.repository.FileAttachmentRepository;
import com.cupidmeet.userdetailsservice.user.repository.UserRepository;
import com.cupidmeet.userdetailsservice.user.service.FileAttachmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileAttachmentServiceImpl implements FileAttachmentService {

    private final UserRepository userRepository;
    private final FileAttachmentRepository fileAttachmentRepository;

    @Override
    @Transactional
    public void addAttachment(UUID userId, FileAttachment attachment) {
        User user = getUserById(userId);
        attachment.setUser(user);
        fileAttachmentRepository.save(attachment);
    }

    @Override
    @Transactional
    public void removeAttachment(UUID attachmentId) {
        FileAttachment attachment = getById(attachmentId);



        attachment.setDeletedAt(LocalDateTime.now());
        fileAttachmentRepository.save(attachment);
    }

    private FileAttachment getById(UUID attachmentId) {
        return fileAttachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new RuntimeException("")
                );
    }

    private User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("")
                );
    }
}

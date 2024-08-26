package com.cupidmeet.userdetailsservice.user.repository;

import com.cupidmeet.userdetailsservice.user.domain.entity.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileAttachmentRepository extends JpaRepository<FileAttachment, UUID> {

}
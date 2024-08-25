package com.cupidmeet.userdetailsservice.user.mapper;

import com.cupidmeet.userdetailsservice.user.domain.dto.FileAttachmentRequest;
import com.cupidmeet.userdetailsservice.user.domain.entity.FileAttachment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FileAttachmentMapper {

  FileAttachment toEntity(FileAttachmentRequest request);
}
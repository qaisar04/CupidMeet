package kz.baltabayev.userdetailsservice.mapper;

import kz.baltabayev.userdetailsservice.model.dto.FileAttachmentRequest;
import kz.baltabayev.userdetailsservice.model.entity.FileAttachment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FileAttachmentMapper {

    /**
     * Converts a {@link FileAttachmentRequest} to a {@link FileAttachment}.
     *
     * @param request The {@link FileAttachmentRequest} to be converted.
     * @return The converted {@link FileAttachment}.
     */
    FileAttachment toEntity(FileAttachmentRequest request);
}
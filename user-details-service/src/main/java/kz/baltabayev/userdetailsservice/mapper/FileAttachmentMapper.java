package kz.baltabayev.userdetailsservice.mapper;

import kz.baltabayev.userdetailsservice.model.dto.FileAttachmentRequest;
import kz.baltabayev.userdetailsservice.model.entity.FileAttachment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
    @Mapping(source = "name", target = "name")
    @Mapping(source = "fileId", target = "fileId")
    FileAttachment toEntity(FileAttachmentRequest request);
}
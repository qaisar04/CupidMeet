package kz.baltabayev.userdetailsservice.mapper;

import kz.baltabayev.userdetailsservice.model.dto.FileAttachmentResponse;
import kz.baltabayev.userdetailsservice.model.entity.FileAttachment;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.*;

@Mapper(componentModel = ComponentModel.SPRING)
public interface FileAttachmentMapper {

    FileAttachmentResponse fromEntity(FileAttachment fileAttachment);
}
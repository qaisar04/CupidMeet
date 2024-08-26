package kz.baltabayev.storageservice.mapper;

import kz.baltabayev.storageservice.domain.dto.FileInfoDto;
import kz.baltabayev.storageservice.domain.entity.FileInfo;
import org.mapstruct.Mapper;

/**
 * Interface responsible for mapping data from FileInfo object to DTO.
 */
@Mapper(componentModel = "spring")
public interface FileInfoMapper {

    FileInfoDto toFileInfoDto(FileInfo fileInfo);
}
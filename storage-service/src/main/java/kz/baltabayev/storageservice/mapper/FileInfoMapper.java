package kz.baltabayev.storageservice.mapper;

import kz.baltabayev.storageservice.domain.dto.FileInfoResponse;
import kz.baltabayev.storageservice.domain.entity.FileInfo;
import org.mapstruct.Mapper;

/**
 * Интерфейс для маппинга сущностей.
 */
@Mapper(componentModel = "spring")
public interface FileInfoMapper {

    /**
     * Преобразует сущность {@link FileInfo} в объект {@link FileInfoResponse}.
     *
     * @param fileInfo Сущность {@link FileInfo}
     * @return Объект {@link FileInfoResponse}
     */
    FileInfoResponse toFileInfoDto(FileInfo fileInfo);
}
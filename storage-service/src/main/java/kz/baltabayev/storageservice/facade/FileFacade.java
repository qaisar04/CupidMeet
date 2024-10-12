package kz.baltabayev.storageservice.facade;

import kz.baltabayev.storageservice.domain.dto.FileInfoResponse;
import kz.baltabayev.storageservice.domain.model.PreparedResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * Фасад для работы с файлами.
 */
@Component
public interface FileFacade {

    /**
     * Возвращает подготовленный ресурс на основе переданного пути к файлу.
     *
     * @param path Путь к файлу.
     * @return Объект {@link PreparedResource}, содержащий ресурс и информацию о связанном файле.
     */
    PreparedResource getPreparedResourceByPath(String path);

    /**
     * Сохраняет файлы, переданные в виде списка объектов {@link MultipartFile}.
     *
     * @param files Список файлов, которые необходимо сохранить
     * @return Список информации о сохраненных файлах в виде объектов {@link FileInfoResponse}
     */
    List<FileInfoResponse> saveFiles(List<MultipartFile> files);

    /**
     * Удаляет файл по его идентификатору.
     *
     * @param fileId Идентификатор файла
     */
    void deleteFileById(UUID fileId);

    /**
     * Удаляет файлы по списку идентификаторов.
     *
     * @param fileIds Список идентификаторов файлов
     */
    void addFilesToDelete(List<UUID> fileIds);

    /**
     * Сохраняет файл.
     *
     * @param file Файл, который необходимо сохранить
     * @return Информация о сохраненном файле в виде объекта {@link FileInfoResponse}
     */
    FileInfoResponse saveFile(MultipartFile file);
}
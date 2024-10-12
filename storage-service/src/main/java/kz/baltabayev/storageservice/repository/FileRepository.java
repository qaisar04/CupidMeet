package kz.baltabayev.storageservice.repository;

import kz.baltabayev.storageservice.domain.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с сущностью {@link FileInfo}.
 */
public interface FileRepository extends JpaRepository<FileInfo, UUID> {

    /**
     * Ищет информацию о файле по местоположению файла.
     *
     * @param path Путь к файлу.
     * @return Информация о файле.
     */
    Optional<FileInfo> findByPath(String path);
}
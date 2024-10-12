package kz.baltabayev.storageservice.service;

import kz.baltabayev.storageservice.domain.entity.FileInfo;
import kz.baltabayev.storageservice.domain.enumeration.S3Bucket;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * Интерфейс для работы с файлами, их сохранением в S3 и метаинформацией в БД.
 */
public interface FileService {

    /**
     * Сохраняет загруженный пользователем файл в S3.
     *
     * @param file Файл, загруженный пользователем.
     * @return Путь к файлу в хранилище S3.
     */
    String saveFileToS3(MultipartFile file);

    /**
     * Сохраняет файл в S3 по переданным данным.
     *
     * @param file       Данные файла.
     * @param bucketName Название бакета S3.
     * @return Путь к файлу в хранилище S3.
     */
    String saveFileToS3(byte[] file, S3Bucket bucket);

    /**
     * Сохраняет метаинформацию о файле в БД.
     *
     * @param fileInfo Объект FileInfo с данными файла.
     * @return Объект FileInfo с идентификатором.
     */
    FileInfo saveFileInfo(FileInfo fileInfo);

    /**
     * Извлекает файл из S3 по указанному пути.
     *
     * @param path Путь к файлу.
     * @return Ресурс файла из S3.
     */
    Resource getFileFromS3(String path);

    /**
     * Возвращает метаинформацию о файле по указанному пути.
     *
     * @param path Путь к файлу.
     * @return Объект FileInfo с метаинформацией.
     */
    FileInfo getFileInfoByPath(String path);

    /**
     * Добавляет файлы в список для удаления.
     *
     * @param fileIds Список идентификаторов файлов.
     */
    void addFilesToDelete(List<UUID> fileIds);

    /**
     * Обрабатывает плановое удаление файлов.
     * Успешно удалённые файлы стираются из S3 и базы данных.
     */
    void processScheduledDeletions();
}
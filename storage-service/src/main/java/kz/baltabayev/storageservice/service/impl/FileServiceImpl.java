package kz.baltabayev.storageservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.baltabayev.storageservice.domain.entity.FileInfo;
import kz.baltabayev.storageservice.domain.entity.FileToDelete;
import kz.baltabayev.storageservice.exception.S3ResourceNotFoundException;
import kz.baltabayev.storageservice.exception.WritingToStorageFailsException;
import kz.baltabayev.storageservice.repository.DeletionFileRepository;
import kz.baltabayev.storageservice.repository.FileRepository;
import kz.baltabayev.storageservice.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service class is responsible for implementation of business logic. This fulfills saving uploaded
 * by user files into S3 storage, creating appropriate FileInfo object containing significant info
 * about file and returning these files and meta-information to user
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    /**
     * Path to uploaded file on S3 storage
     */
    private static final String S3_URL_TEMPLATE = "s3://%s/%s";

    /**
     * Name of S3 bucket for storing files of business ideas
     */
    @Value("${minio.environment.bucket-name}")
    private String cupidBucketName;

    /**
     * Jpa Repository fulfilling data manipulation operations with database
     */
    private final FileRepository fileRepository;

    private final DeletionFileRepository deletionFileRepository;

    /**
     * Service object for uploading and downloading files to (from) S3 storage
     */
    private final ResourceLoader resourceLoader;
    /**
     * Error message template for entity not found exception. The placeholders %s will be replaced
     * with the actual values.
     */
    private static final String ENTITY_NOT_FOUND_MESSAGE = "Entity with %s: %s does not exist";

    /**
     * Error message template for S3 resource not found exception. The placeholders %s will be
     * replaced with the actual values.
     */
    private static final String S3_RESOURCE_NOT_FOUND_MESSAGE = "File with %s: %s does not exist or cannot be read";

    /**
     * {@inheritDoc}
     */
    @Override
    public FileInfo saveFileInfo(FileInfo fileInfo) {
        return fileRepository.save(fileInfo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String saveFileToS3(MultipartFile file) {
        String s3Url = String.format(S3_URL_TEMPLATE, cupidBucketName, UUID.randomUUID());
        WritableResource resource = (WritableResource) resourceLoader.getResource(s3Url);
        return writeResource(file, resource, s3Url);
    }

    private String writeResource(MultipartFile file, WritableResource resource, String s3Url) {
        try {
            try (OutputStream outputStream = resource.getOutputStream()) {
                outputStream.write(file.getBytes());
                return s3Url;
            }
        } catch (IOException e) {
            throw new WritingToStorageFailsException(e);
        }
    }

    /**
     * Retrieves the file from the S3 storage based on the provided path.
     *
     * @param path The path of the file.
     * @return The Resource representing the file retrieved from the S3 storage.
     * @throws S3ResourceNotFoundException If the file does not exist or is not readable in the S3
     *                                     storage.
     */
    @Override
    public Resource getFileFromS3(String path) {
        Resource resource = resourceLoader.getResource(path);
        validateResource(resource, path);
        return resourceLoader.getResource(path);
    }

    /**
     * Retrieves the meta information of the file based on the provided path.
     *
     * @param path The path of the file.
     * @return The FileInfo object containing the meta information of the file.
     * @throws EntityNotFoundException If the file meta information does not exist in the database.
     */
    @Override
    public FileInfo getFileInfoByPath(String path) {
        return fileRepository.findByPath(path).orElseThrow(() ->
                new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "path", path)));
    }

    /**
     * Validates the existence and readability of the resource.
     *
     * @param resource The resource to be validated.
     * @param path     The path of the resource for error messaging.
     * @throws S3ResourceNotFoundException if the resource does not exist or is not readable.
     */
    private void validateResource(Resource resource, String path) {
        if (!resource.exists() && !resource.isReadable()) {
            throw new S3ResourceNotFoundException(String.format(S3_RESOURCE_NOT_FOUND_MESSAGE, "path", path));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void addFilesToDelete(List<UUID> fileIDs) {
        List<FileToDelete> deletionFiles = fileRepository.findAllById(fileIDs).stream()
                .peek(fileInfo -> fileInfo.setDeletedAt(LocalDateTime.now()))
                .map(fileInfo -> FileToDelete.builder()
                        .fileInfo(fileInfo)
                        .build())
                .toList();
        deletionFileRepository.saveAll(deletionFiles);
    }

    /**
     * Processes the scheduled deletions of files. Finds all files marked for deletion,
     * attempts to delete them, and removes successfully deleted files from the repository.
     */
    @Override
    @Transactional
    public void processScheduledDeletions() {
        List<FileToDelete> deletionFiles = deletionFileRepository.findAll();
        List<FileInfo> successfullyDeletedFiles = new ArrayList<>();

        for (FileToDelete deletionFile : deletionFiles) {
            tryDeleteFile(deletionFile, successfullyDeletedFiles);
        }

        deleteFilesFromRepository(successfullyDeletedFiles);
    }

    /**
     * Attempts to delete a file marked for deletion. If the file is successfully deleted,
     * it is added to the list of successfully deleted files.
     *
     * @param deletionFile             the file marked for deletion
     * @param successfullyDeletedFiles the list of successfully deleted files
     */
    private void tryDeleteFile(FileToDelete deletionFile, List<FileInfo> successfullyDeletedFiles) {
        FileInfo fileInfo = deletionFile.getFileInfo();
        String filePath = fileInfo.getPath();

        Resource resource = resourceLoader.getResource(filePath);
        validateResource(resource, filePath);

        try {
            if (resource instanceof WritableResource) {
                ((WritableResource) resource).getOutputStream().close();
                successfullyDeletedFiles.add(fileInfo);
            } else {
                log.error("File with ID {} is not deleted.", fileInfo.getId());
            }
        } catch (IOException e) {
            throw new WritingToStorageFailsException(e);
        }
    }

    /**
     * Deletes the list of successfully deleted files from the repository.
     *
     * @param successfullyDeletedFiles the list of successfully deleted files
     */
    private void deleteFilesFromRepository(List<FileInfo> successfullyDeletedFiles) {
        if (!successfullyDeletedFiles.isEmpty()) {
            fileRepository.deleteAll(successfullyDeletedFiles);
        }
    }
}
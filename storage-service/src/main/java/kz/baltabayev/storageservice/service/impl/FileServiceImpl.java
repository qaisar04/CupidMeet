package kz.baltabayev.storageservice.service.impl;

import com.cupidmeet.runtimecore.exception.CustomRuntimeException;
import kz.baltabayev.storageservice.config.BucketProperties;
import kz.baltabayev.storageservice.domain.entity.FileInfo;
import kz.baltabayev.storageservice.domain.entity.FileToDelete;
import kz.baltabayev.storageservice.domain.enumeration.S3Bucket;
import kz.baltabayev.storageservice.messages.Messages;
import kz.baltabayev.storageservice.repository.DeletionFileRepository;
import kz.baltabayev.storageservice.repository.FileRepository;
import kz.baltabayev.storageservice.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    /**
     * Путь к загруженному файлу в хранилище S3
     */
    private static final String S3_URL_TEMPLATE = "s3://%s/%s";

    private final FileRepository fileRepository;
    private final DeletionFileRepository deletionFileRepository;
    private final ResourceLoader resourceLoader;
    private final BucketProperties bucket;

    @Override
    public FileInfo saveFileInfo(FileInfo fileInfo) {
        return fileRepository.save(fileInfo);
    }

    @Override
    public String saveFileToS3(MultipartFile file) {
        String s3Url = String.format(S3_URL_TEMPLATE, bucket.getBucketName(S3Bucket.MAIN), UUID.randomUUID());
        WritableResource resource = (WritableResource) resourceLoader.getResource(s3Url);
        return writeResource(file, resource, s3Url);
    }

    @Override
    public String saveFileToS3(byte[] file, S3Bucket bucketName) {
        UUID fileName = UUID.randomUUID();
        String s3Url = String.format(S3_URL_TEMPLATE, bucket.getBucketName(bucketName), fileName);
        WritableResource resource = (WritableResource) resourceLoader.getResource(s3Url);
        saveFileInfo(FileInfo.from(file, fileName + ".png", "image/png", s3Url));
        return writeResource(file, resource, s3Url);
    }

    @Override
    public Resource getFileFromS3(String path) {
        Resource resource = resourceLoader.getResource(path);
        validateResource(resource, path);
        return resourceLoader.getResource(path);
    }

    @Override
    public FileInfo getFileInfoByPath(String path) {
        return fileRepository.findByPath(path).orElseThrow(() ->
                new CustomRuntimeException(Messages.FILE_NOT_FOUND, path));
    }

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

    private String writeResource(MultipartFile file, WritableResource resource, String s3Url) {
        try {
            try (OutputStream outputStream = resource.getOutputStream()) {
                outputStream.write(file.getBytes());
                return s3Url;
            }
        } catch (IOException e) {
            throw new CustomRuntimeException(Messages.WRITING_TO_STORAGE_FAILS);
        }
    }

    private String writeResource(byte[] file, WritableResource resource, String s3Url) {
        try {
            try (OutputStream outputStream = resource.getOutputStream()) {
                outputStream.write(file);
                return s3Url;
            }
        } catch (IOException e) {
            throw new CustomRuntimeException(Messages.WRITING_TO_STORAGE_FAILS);
        }
    }

    private void validateResource(Resource resource, String path) {
        if (!resource.exists() && !resource.isReadable()) {
            throw new CustomRuntimeException(Messages.FILE_NOT_FOUND, path);
        }
    }

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
                log.error("Файл с идентификатором {} не был удален.", fileInfo.getId());
            }
        } catch (IOException e) {
            throw new CustomRuntimeException(Messages.WRITING_TO_STORAGE_FAILS);
        }
    }

    private void deleteFilesFromRepository(List<FileInfo> successfullyDeletedFiles) {
        if (!successfullyDeletedFiles.isEmpty()) {
            fileRepository.deleteAll(successfullyDeletedFiles);
        }
    }
}
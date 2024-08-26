package kz.baltabayev.storageservice.facade.impl;

import kz.baltabayev.storageservice.mapper.FileInfoMapper;
import kz.baltabayev.storageservice.service.FileService;
import kz.baltabayev.storageservice.domain.dto.FileInfoDto;
import kz.baltabayev.storageservice.domain.entity.FileInfo;
import kz.baltabayev.storageservice.domain.model.PreparedResource;
import kz.baltabayev.storageservice.facade.FileFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Facade implementation for working with files. This component acts as an intermediary between the
 * controller and the service, providing a simplified interface for retrieving prepared resources
 * based on a given file path, also mapping entity and dto.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FileFacadeImpl implements FileFacade {

    private final FileService fileService;
    private final FileInfoMapper mapper;

    /**
     * Retrieves a prepared resource based on the provided file path.
     *
     * @param path The path of the file.
     * @return The PreparedResource object containing the resource and associated file information.
     */
    @Override
    public PreparedResource getPreparedResourceByPath(String path) {
        Resource resource = fileService.getFileFromS3(path);
        FileInfo fileInfo = fileService.getFileInfoByPath(path);
        return new PreparedResource(resource, fileInfo);
    }

    /**
     * {@inheritDoc} Fulfils business logic inside transaction
     */
    @Transactional
    @Override
    public List<FileInfoDto> saveFiles(List<MultipartFile> files) {
        List<FileInfoDto> savedFilesInfo = new ArrayList<>();
        for (MultipartFile file : files) {
            savedFilesInfo.add(saveFile(file));
        }
        return savedFilesInfo;
    }

    /**
     * Deletes a file by its ID.
     *
     * @param fileId The ID of the file to delete.
     */
    @Override
    @Transactional
    public void deleteFileById(UUID fileId) {
        fileService.addFilesToDelete(List.of(fileId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addFilesToDelete(List<UUID> fileIDs) {
        fileService.addFilesToDelete(fileIDs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public FileInfoDto saveFile(MultipartFile file) {
        String s3Url = fileService.saveFileToS3(file);
        FileInfo fileInfo = fileService.saveFileInfo(FileInfo.from(file, s3Url));
        return mapper.toFileInfoDto(fileInfo);
    }
}
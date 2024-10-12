package kz.baltabayev.storageservice.facade.impl;

import kz.baltabayev.storageservice.domain.dto.FileInfoResponse;
import kz.baltabayev.storageservice.domain.entity.FileInfo;
import kz.baltabayev.storageservice.domain.model.PreparedResource;
import kz.baltabayev.storageservice.facade.FileFacade;
import kz.baltabayev.storageservice.mapper.FileInfoMapper;
import kz.baltabayev.storageservice.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileFacadeImpl implements FileFacade {

    private final FileService fileService;
    private final FileInfoMapper mapper;

    @Override
    public PreparedResource getPreparedResourceByPath(String path) {
        Resource resource = fileService.getFileFromS3(path);
        FileInfo fileInfo = fileService.getFileInfoByPath(path);
        return new PreparedResource(resource, fileInfo);
    }

    @Transactional
    @Override
    public List<FileInfoResponse> saveFiles(List<MultipartFile> files) {
        List<FileInfoResponse> savedFilesInfo = new ArrayList<>();
        for (MultipartFile file : files) {
            savedFilesInfo.add(saveFile(file));
        }
        return savedFilesInfo;
    }

    @Override
    @Transactional
    public void deleteFileById(UUID fileId) {
        fileService.addFilesToDelete(List.of(fileId));
    }

    @Override
    public void addFilesToDelete(List<UUID> fileIDs) {
        fileService.addFilesToDelete(fileIDs);
    }

    @Override
    @Transactional
    public FileInfoResponse saveFile(MultipartFile file) {
        String s3Url = fileService.saveFileToS3(file);
        FileInfo fileInfo = fileService.saveFileInfo(FileInfo.from(file, s3Url));
        return mapper.toFileInfoDto(fileInfo);
    }
}
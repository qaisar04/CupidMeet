package kz.baltabayev.storageservice.service;

import kz.baltabayev.storageservice.domain.entity.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * Public interface responsible for implementation of business logic. This declares method on saving
 * business idea files into external storage and meta information of them into database, as well as
 * method on returning these data to user.
 */
public interface FileService {

    /**
     * Service method for saving file uploaded by user into S3 storage
     *
     * @param file Single business idea file uploaded by user
     * @return String Path to file on S3 storage
     */
    String saveFileToS3(MultipartFile file);

    /**
     * Method on saving FileInfo entity object into database
     *
     * @param fileInfo Data about business idea file stored in S3
     * @return FileInfo object with its id
     */
    FileInfo saveFileInfo(FileInfo fileInfo);

    /**
     * Retrieves the file from S3 storage based on the provided path.
     *
     * @param path The path of the file.
     * @return The Resource representing the file retrieved from S3 storage.
     */
    Resource getFileFromS3(String path);

    /**
     * Retrieves the meta information of the file based on the provided path.
     *
     * @param path The path of the file.
     * @return The FileInfo object containing the meta information of the file.
     */
    FileInfo getFileInfoByPath(String path);

    /**
     * Method to add files to the list for subsequent deletion.
     *
     * @param fileIDs List of file IDs to add to deletion.
     */
    void addFilesToDelete(List<UUID> fileIDs);

    /**
     * Method to process scheduled file deletions.
     * If processed successfully, the files are deleted from S3 and storage.
     */
    void processScheduledDeletions();
}
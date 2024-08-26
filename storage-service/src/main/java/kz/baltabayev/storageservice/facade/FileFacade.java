package kz.baltabayev.storageservice.facade;

import kz.baltabayev.storageservice.domain.dto.FileInfoDto;
import kz.baltabayev.storageservice.domain.model.PreparedResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * @author Daniil Hromau
 * <p>
 * Facade is using as converting layer between controller and service to separate the logic of
 * working with dto and entity and mapper's injection
 */
@Component
public interface FileFacade {

    PreparedResource getPreparedResourceByPath(String path);

    /**
     * Method covers complex logic on saving file in S3 storage, extracting and saving meta
     * information about file in database and returning it to calling method as DTO
     *
     * @param files List of business idea files
     * @return List<FileInfoDto> list dto for returning to calling method
     */
    List<FileInfoDto> saveFiles(List<MultipartFile> files);

    /**
     * Deletes a file by its ID.
     *
     * @param fileId The ID of the file to delete.
     */
    void deleteFileById(UUID fileId);

    /**
     * Deletes files based on the provided file IDs. This method accepts a list of file IDs and
     * deletes the files corresponding to those IDs.
     *
     * @param fileIds A list of UUIDs representing the IDs of the files to be deleted.
     */
    void addFilesToDelete(List<UUID> fileIds);

    /**
     * Saves the given file.
     *
     * @param file The file to be saved.
     * @return Information about the saved file as a {@link FileInfoDto}.
     */
    FileInfoDto saveFile(MultipartFile file);
}
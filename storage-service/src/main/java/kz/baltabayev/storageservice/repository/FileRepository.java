package kz.baltabayev.storageservice.repository;

import kz.baltabayev.storageservice.domain.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Jpa Repository class provides data manipulation operations with database
 */
public interface FileRepository extends JpaRepository<FileInfo, UUID> {

    /**
     * Method saves single FileInfo object into database
     *
     * @param fileInfo FileInfo object representing meta-information about stored file
     * @return FileInfo object with added unique identifier and auditing information
     */
    FileInfo save(FileInfo fileInfo);

    /**
     * Retrieves an optional FileInfo object from the database based on the provided path.
     *
     * @param path The path of the file.
     * @return An Optional containing the FileInfo object if found, or an empty Optional if not found.
     */
    Optional<FileInfo> findByPath(String path);
}
package kz.baltabayev.storageservice.repository;

import kz.baltabayev.storageservice.domain.entity.FileToDelete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Репозиторий для работы с сущностью {@link FileToDelete}.
 */
@Repository
public interface DeletionFileRepository extends JpaRepository<FileToDelete, UUID> {

}
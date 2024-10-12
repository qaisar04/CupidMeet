package kz.baltabayev.storageservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Сущность, хранящая информацию о файле, который необходимо удалить.
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "files_to_delete")
public class FileToDelete {

    /**
     * Идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Сущность, связанная с файлом, которая хранит информацию о файле.
     */
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private FileInfo fileInfo;
}
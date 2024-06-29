package kz.baltabayev.storageservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Entity representing a file that is marked for deletion.
 *
 * <p>This entity is stored in the "files_to_delete" table and represents a
 * file that is scheduled to be deleted from the storage. The associated {@link FileInfo} entity is
 * also removed when this entity is deleted due to the {@code CascadeType.REMOVE}
 * configuration.</p>
 *
 * <p>Fields:</p>
 * <ul>
 *   <li>{@code id} - The unique identifier for this entity. Generated using {@code GenerationType.UUID}.</li>
 *   <li>{@code fileInfo} - The {@link FileInfo} entity associated with this file to delete.
 *       Configured with {@code CascadeType.REMOVE} to automatically delete the associated {@code FileInfo}
 *       when this entity is deleted.</li>
 * </ul>
 */
@Getter
@Setter
@Entity
@Builder
@Table(name = "files_to_delete")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FileToDelete {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private FileInfo fileInfo;
}
package kz.baltabayev.storageservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * Class represents file stored at external storage. Class describes file path to storage and
 * meta-information (name, size, content-type), as well as timing marks when file was created,
 * updated or deleted, inherited from AuditingEntity class.
 */
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor()
@Builder
@Entity
@Table(name = "files", schema = "storage")
public class FileInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String path;
    private String name;
    private long size;
    private String contentType;

    @OneToOne(mappedBy = "fileInfo", cascade = CascadeType.REMOVE)
    private FileToDelete fileToDelete;

    /**
     * Factory method encapsulates logic of creating new FileInfo instance from file uploaded by user
     * and provided url to file in S3 storage
     *
     * @param file  Business idea file saved in S3 storage
     * @param s3Url Path to the file on S3 storage
     * @return FileInfo instance
     */
    public static FileInfo from(MultipartFile file, String s3Url) {
        return FileInfo.builder()
                .path(s3Url)
                .name(file.getOriginalFilename())
                .size(file.getSize())
                .contentType(file.getContentType())
                .build();
    }
}

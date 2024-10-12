package kz.baltabayev.storageservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * Сущность, хранящая информацию о файле.
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name = "files", schema = "storage")
@EqualsAndHashCode(of = "id", callSuper = false)
public class FileInfo extends BaseEntity {

    /**
     * Идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Путь к файлу.
     */
    private String path;

    /**
     * Имя файла.
     */
    private String name;

    /**
     * Размер файла.
     */
    private long size;

    /**
     * Тип контента.
     */
    private String contentType;

    /**
     * Сущность, связанная с файлом, которая хранит информацию о времени удаления файла.
     */
    @OneToOne(mappedBy = "fileInfo", cascade = CascadeType.REMOVE)
    private FileToDelete fileToDelete;

    /**
     * Создает новый экземпляр класса на основе переданного файла и пути к файлу.
     *
     * @param file файл, на основе которого создается экземпляр класса FileInfo
     * @param s3Url путь к файлу
     * @return новый экземпляр класса FileInfo
     */
    public static FileInfo from(MultipartFile file, String s3Url) {
        return FileInfo.builder()
                .path(s3Url)
                .name(file.getOriginalFilename())
                .size(file.getSize())
                .contentType(file.getContentType())
                .build();
    }

    /**
     * Создает новый экземпляр класса на основе переданных данных о файле.
     *
     * @param data данные файла
     * @param fileName имя файла
     * @param contentType тип контента
     * @param s3Url путь к файлу
     * @return новый экземпляр класса FileInfo
     */
    public static FileInfo from(byte[] data, String fileName, String contentType, String s3Url) {
        return FileInfo.builder()
                .path(s3Url)
                .name(fileName)
                .size((long) data.length)
                .contentType(contentType)
                .build();
    }
}
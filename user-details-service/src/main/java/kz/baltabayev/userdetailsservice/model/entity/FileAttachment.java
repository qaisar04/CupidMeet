package kz.baltabayev.userdetailsservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Represents a file attachment entity in the system.
 * This entity contains information about a file attached to a user's profile.
 * Extends the {@link BaseEntity} class to include common entity properties.
 * Uses Lombok annotations for boilerplate code reduction.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "files_attachment")
@EqualsAndHashCode(callSuper = true)
public class FileAttachment extends BaseEntity {

    /**
     * The unique identifier of the file attachment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the file.
     */
    @Column(name = "file_name", nullable = false)
    private String fileName;

    /**
     * The source of the file.
     */
    @Column(nullable = false)
    private String source;

    /**
     * The URL where the file is stored.
     */
    @Column(nullable = false)
    private String url;

    /**
     * The user information to which this file attachment belongs.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_info_id", nullable = false)
    private UserInfo userInfo;

    /**
     * Constructs a new FileAttachment with the specified file name, source, URL, and user information.
     *
     * @param fileName The name of the file.
     * @param source   The source of the file.
     * @param url      The URL where the file is stored.
     * @param userInfo The user information to which this file attachment belongs.
     */
    public FileAttachment(String fileName, String source, String url, UserInfo userInfo) {
        this.fileName = fileName;
        this.source = source;
        this.url = url;
        this.userInfo = userInfo;
    }
}
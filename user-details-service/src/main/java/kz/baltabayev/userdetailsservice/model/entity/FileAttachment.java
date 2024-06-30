package kz.baltabayev.userdetailsservice.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

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
@Table(name = "files_attachment", schema = "user_detail")
@EqualsAndHashCode(callSuper = true)
public class FileAttachment extends BaseEntity {

    /**
     * The unique identifier of the file attachment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The unique identifier of the file associated with the attachment.
     */
    @Column(name = "file_id")
    private UUID fileId;

    /**
     * The name of the file.
     */
    private String name;

    /**
     * The content type of the file attachment.
     */
    @Column(name = "content_type")
    private String contentType;

    /**
     * The path to the file attachment.
     */
    private String path;

    /**
     * The user information to which this file attachment belongs.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private UserInfo userInfo;

    /**
     * The timestamp when the file attachment was deleted.
     */
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
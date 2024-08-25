package com.cupidmeet.userdetailsservice.user.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Представляет сущность вложения файла, связанного с идеей. Этот класс расширяет класс BaseEntity
 * и включает поля, такие как fileId, name, contentType, path, deletedAt и связанный пользователь.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = "user")
@Table(name = "files_attachment")
public class FileAttachment extends BaseEntity {

    /**
     * Уникальный идентификатор файла, связанного с вложением.
     */
    @Column(name = "file_id")
    private UUID fileId;

    /**
     * Описание файла во вложении.
     */
    private String name;

    /**
     * Тип содержимого файла во вложении.
     */
    @Column(name = "content_type")
    private String contentType;

    /**
     * Путь к файлу во вложении.
     */
    private String path;

    /**
     * Время, когда вложение файла было удалено.
     */
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    /**
     * Пользователь, связанный с этим вложением файла.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
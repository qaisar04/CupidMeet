package ru.polskiy.feedbackservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Base class for entities in the application, providing common fields like id, createdAt, and updatedAt.
 *
 * @MappedSuperclass indicate that its fields should be mapped to the database
 * columns when extended by  entity classes.
 */
@Data
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * Primary key for the entity. Auto-generated with strategy GenerationType.IDENTITY.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Date and time when the entity was created.
     * Sets automatically.
     */
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * Date and time when the entity was last updated.
     * Sets automatically.
     */
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
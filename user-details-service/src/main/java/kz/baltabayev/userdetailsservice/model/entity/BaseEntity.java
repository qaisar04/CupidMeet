package kz.baltabayev.userdetailsservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Represents a base entity class with common fields such as id, createdAt, and updatedAt.
 * This class serves as a superclass for other entity classes to inherit common attributes
 * and to avoid code duplication.
 */
@Data
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * The date and time when the entity was created.
     */
    @CreationTimestamp
    @Column(name = "created_at")
    protected LocalDateTime createdAt;

    /**
     * The date and time when the entity was last updated.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;
}
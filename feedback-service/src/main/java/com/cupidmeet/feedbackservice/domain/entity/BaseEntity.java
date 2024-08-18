package com.cupidmeet.feedbackservice.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Представляет базовый класс сущности с общими полями.
 */
@Data
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * Уникальный идентификатор сущности.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Дата и время создания сущности.
     */
    @CreationTimestamp
    @Column(name = "created_at")
    protected LocalDateTime createdAt;

    /**
     * Дата и время последнего обновления сущности.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;
}
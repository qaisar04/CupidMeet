package kz.baltabayev.storageservice.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Abstract class provides fields for classes-descendants to make database audit on timing of
 * creating, updating and deleting entity. This allows to differentiate between business logic and
 * audit
 */
@Getter
@Setter
@ToString
@MappedSuperclass
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

  /**
   * The timestamp representing the creation time of the entity.
   */
  @CreationTimestamp
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  /**
   * The timestamp representing the last update time of the entity.
   */
  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  /**
   * The timestamp representing the deletion time of the entity.
   */
  @UpdateTimestamp
  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;
}

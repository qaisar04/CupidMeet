package kz.baltabayev.event.listener.extension.model.dto;

import lombok.*;
import kz.baltabayev.event.listener.extension.model.type.Status;

import java.time.LocalDateTime;

/**
 * AuditEvent is a data transfer object (DTO) class that represents an audit event.
 * It uses Lombok annotations for automatic generation of getters, setters, a builder, and constructors.
 * An audit event contains the following fields:
 * - userUUID: the UUID of the user associated with the event
 * - action: the action performed that triggered the event
 * - status: the status of the event (e.g., SUCCESS or FAIL)
 * - startedAt: the time when the event started
 * - finishedAt: the time when the event finished
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditEvent {

  private String userUUID;
  private String action;
  private Status status;
  private LocalDateTime startedAt;
  private LocalDateTime finishedAt;
}
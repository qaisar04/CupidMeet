package com.cupidmeet.event.listener.extension.model.dto;

import com.cupidmeet.event.listener.extension.model.type.Status;
import lombok.*;

import java.time.LocalDateTime;

/**
 * AuditEvent is a data transfer object (DTO) class that represents an audit event.
 * It uses Lombok annotations for automatic generation of getters, setters, a builder, and constructors.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditEvent {

    private String userId;
    private String action;
    private Status status;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
}
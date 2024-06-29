package kz.baltabayev.userdetailsservice.model.dto;

import java.util.UUID;

public record FileAttachmentRequest(
        UUID fileId,
        String name,
        String contentType,
        String path
) {
}
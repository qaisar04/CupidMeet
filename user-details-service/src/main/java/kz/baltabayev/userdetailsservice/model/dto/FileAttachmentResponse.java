package kz.baltabayev.userdetailsservice.model.dto;

import java.util.UUID;

public record FileAttachmentResponse(
        UUID fileId,
        String name,
        String contentType,
        String path
) {
}

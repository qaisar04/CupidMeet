package kz.baltabayev.userdetailsservice.model.payload;

import java.util.UUID;

public record FileUploadResponse(
        UUID id,
        String path,
        String name,
        String contentType
) {
}
package kz.baltabayev.userdetailsservice.model.dto;

public record FileUploadResponse(
        String id,
        String source,
        String url
) {
}
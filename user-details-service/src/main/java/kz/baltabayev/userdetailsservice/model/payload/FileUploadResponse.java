package kz.baltabayev.userdetailsservice.model.payload;

public record FileUploadResponse(
        String id,
        String source,
        String url
) {
}
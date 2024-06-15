package kz.baltabayev.userdetailsservice.model.payload;

public record FileUploadResponse(
        String fileName,
        String source,
        String url
) {
}
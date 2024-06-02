package kz.baltabayev.userdetailsservice.model.dto;

public record FileAttachmentResponse(
        String fileName,
        String source,
        String url
) {
}

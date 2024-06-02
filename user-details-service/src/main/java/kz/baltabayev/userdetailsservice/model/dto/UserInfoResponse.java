package kz.baltabayev.userdetailsservice.model.dto;

import java.util.List;

public record UserInfoResponse(
        String name,
        Integer age,
        String city,
        String personalityType,
        String bio,
        List<FileAttachmentResponse> files
) {
}
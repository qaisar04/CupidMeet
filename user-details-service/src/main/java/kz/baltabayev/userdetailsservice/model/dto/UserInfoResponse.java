package kz.baltabayev.userdetailsservice.model.dto;

import kz.baltabayev.userdetailsservice.model.types.PersonalityType;

import java.util.List;

public record UserInfoResponse(
        String name,
        Integer age,
        String city,
        PersonalityType personalityType,
        String bio,
        List<FileAttachmentResponse> files
) {
}
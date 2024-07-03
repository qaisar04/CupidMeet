package kz.baltabayev.userdetailsservice.model.dto;

import kz.baltabayev.userdetailsservice.model.types.PersonalityType;

import java.util.Set;

public record UserMatchResponse(
        Long userId,
        String name,
        String city,
        Integer age,
        PersonalityType personalityType,
        String bio,
        Set<String> files
) {
}
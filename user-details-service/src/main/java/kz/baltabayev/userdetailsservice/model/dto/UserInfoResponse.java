package kz.baltabayev.userdetailsservice.model.dto;

import kz.baltabayev.userdetailsservice.model.types.Gender;
import kz.baltabayev.userdetailsservice.model.types.PersonalityType;

import java.util.Set;

public record UserInfoResponse(
        String name,
        Integer age,
        String city,
        Gender gender,
        PersonalityType personalityType,
        String bio,
        Set<String> files
) {
}
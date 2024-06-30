package kz.baltabayev.userdetailsservice.model.dto;

import kz.baltabayev.userdetailsservice.model.types.PreferredGender;

public record UserPreferenceResponse(
        PreferredGender preferredGender,
        Integer minAge,
        Integer maxAge
) {
}
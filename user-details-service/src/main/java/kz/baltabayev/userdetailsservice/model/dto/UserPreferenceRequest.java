package kz.baltabayev.userdetailsservice.model.dto;

public record UserPreferenceRequest
        (
                String gender,
                Integer minAge,
                Integer maxAge
        ) {
}
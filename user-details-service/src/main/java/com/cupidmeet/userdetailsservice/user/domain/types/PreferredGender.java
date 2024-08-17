package com.cupidmeet.userdetailsservice.user.domain.types;

public enum PreferredGender {
    MALE,
    FEMALE,
    ANY;

    public static PreferredGender fromString(String preferredGender) {
        try {
            return PreferredGender.valueOf(preferredGender.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid preferred gender: " + preferredGender);
        }
    }
}
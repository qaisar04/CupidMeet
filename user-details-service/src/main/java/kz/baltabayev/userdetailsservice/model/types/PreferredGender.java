package kz.baltabayev.userdetailsservice.model.types;

/**
 * Represents the preferred gender of a user for matching purposes.
 * This enum defines the possible values for a user's preferred gender.
 *
 * The possible values are:
 * <ul>
 *   <li>{@code MALE} - Prefers male gender.</li>
 *   <li>{@code FEMALE} - Prefers female gender.</li>
 *   <li>{@code ANY} - No preference for gender.</li>
 * </ul>
 */
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
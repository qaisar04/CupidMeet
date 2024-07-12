package kz.baltabayev.userdetailsservice.model.dto;

/**
 * Request body for creating a new user along with their additional information.
 *
 * @param createRequest   The request body containing the user ID and username.
 * @param userInfoRequest The request body containing additional user information.
 */
public record UserAndInfoCreateRequest(
        UserCreateRequest createRequest,
        UserInfoRequest userInfoRequest
) {
}

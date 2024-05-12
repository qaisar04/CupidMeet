package kz.baltabayev.userinfosecuritystarter.context;

import kz.baltabayev.userinfosecuritystarter.model.UserInfo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * A utility class to manage user information within the SecurityContext of the application.
 */
public class UserLoader {

    private static final String USER_CREDENTIALS = "";

    /**
     * Stores the authenticated user's information into the SecurityContext.
     * @param userInfo UserInfo containing details about the user.
     */
    public void putIntoContext(UserInfo userInfo) {
        UsernamePasswordAuthenticationToken customAuthentication
                = new UsernamePasswordAuthenticationToken(
                userInfo, USER_CREDENTIALS, userInfo.getAuthorities());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(customAuthentication);
    }

    /**
     * Retrieves the UserInfo from the current SecurityContext.
     * @return UserInfo of the currently authenticated user.
     */
    public UserInfo getUserInfo() {
        SecurityContext context = SecurityContextHolder.getContext();
        return (UserInfo) context.getAuthentication().getPrincipal();
    }
}
package kz.baltabayev.userinfosecuritystarter.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.baltabayev.userinfosecuritystarter.context.UserLoader;
import kz.baltabayev.userinfosecuritystarter.parser.JwtParser;
import kz.baltabayev.userinfosecuritystarter.model.UserInfo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * A Spring Security filter that runs once per request to map user info from JWT to SecurityContext.
 */
@Component
@RequiredArgsConstructor
public class UserInfoMappingFilter extends OncePerRequestFilter {

    private final JwtParser jwtParser;
    private final UserLoader userLoader;

    /**
     * Processes each HTTP request to extract the user's information from JWT and load it into the SecurityContext.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain
     */
    @Override
    @SneakyThrows
    protected void doFilterInternal
            (
                    @NonNull HttpServletRequest request,
                    @NonNull HttpServletResponse response,
                    FilterChain filterChain
            ) {
        UserInfo userInfo = jwtParser.getUserInfoFromToken(request);
        userLoader.putIntoContext(userInfo);
        filterChain.doFilter(request, response);
    }
}
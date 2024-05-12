package kz.baltabayev.userinfosecuritystarter.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import kz.baltabayev.userinfosecuritystarter.model.UserInfo;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;

import java.text.ParseException;

/**
 * A parser for JWT tokens that extracts and validates user information from JWTs contained in HTTP requests.
 */
public class JwtParser {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String EMPTY_STRING = "";
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Retrieves and validates the user info from the JWT token present in the HTTP request.
     * @param request the HTTP request containing the JWT token in its Authorization header.
     * @return UserInfo object containing extracted user data.
     * @throws ParseException if there is an error parsing the JWT.
     * @throws IllegalArgumentException if the token is empty or in an invalid format.
     */
    public UserInfo getUserInfoFromToken(
            HttpServletRequest request
    ) throws ParseException {
        String token = extractTokenFromRequest(request);
        if (token.isEmpty()) {
            throw new IllegalArgumentException("Token is empty or invalid format");
        }
        return decodeAndValidateToken(token);
    }

    /**
     * Decodes the JWT token and converts its claims to a UserInfo object.
     * @param token the JWT token as a String.
     * @return UserInfo object representing the user detailed in the token's claims.
     * @throws ParseException if the JWT cannot be parsed.
     */
    @SneakyThrows
    private UserInfo decodeAndValidateToken(String token) throws ParseException {
        SignedJWT signedJwt = SignedJWT.parse(token);
        String claims = signedJwt.getPayload().toString();
        return objectMapper.readValue(claims, UserInfo.class);
    }

    /**
     * Extracts the JWT token from the Authorization header of the request.
     * @param request the HTTP request from which to extract the JWT token.
     * @return the JWT token as a String, or an empty string if the token is not present or incorrectly formatted.
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(7);
        }
        return EMPTY_STRING;
    }
}
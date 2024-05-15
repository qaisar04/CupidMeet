package kz.baltabayev.authservice.service.impl;

import kz.baltabayev.authservice.service.AuthenticationService;
import kz.baltabayev.authservice.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final RedisTemplate<String, Long> userRedisTemplate;
    private final RedisTemplate<String, String> sessionRedisTemplate;
    private final JwtTokenProvider jwtTokenProvider;

    private final SecureRandom secureRandom = new SecureRandom();

    @Autowired
    public AuthenticationServiceImpl(
            @Qualifier("userRedisTemplate") RedisTemplate<String, Long> userRedisTemplate,
            @Qualifier("sessionRedisTemplate") RedisTemplate<String, String> sessionRedisTemplate,
            JwtTokenProvider jwtTokenProvider) {
        this.userRedisTemplate = userRedisTemplate;
        this.sessionRedisTemplate = sessionRedisTemplate;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private static final long CODE_EXPIRATION_MINUTES = 10;
    private static final String SESSION_PREFIX = "session:";
    private static final String CODE_PREFIX = "code:";

    /**
     * Validates the provided security code.
     *
     * @param securityCode The security code to validate.
     * @return The generated JWT token if the security code is valid.
     * @throws IllegalArgumentException if the code is invalid or expired.
     */
    @Override
    public String validate(long securityCode) {
        String key = CODE_PREFIX + securityCode;
        Long userId = userRedisTemplate.opsForValue().get(key);

        if (userId == null) {
            throw new IllegalArgumentException("Invalid or expired code");
        }

        userRedisTemplate.delete(CODE_PREFIX + securityCode);

        String generatedToken = SESSION_PREFIX + jwtTokenProvider.generateToken(userId);
        sessionRedisTemplate.opsForValue().set(String.valueOf(userId), generatedToken, Duration.ofMinutes(60));
        return generatedToken;
    }

    /**
     * Generates a security code for the given user ID.
     *
     * @param userId The ID of the user for whom the code is generated.
     * @return The generated security code.
     */
    @Override
    public long generateSecurityCode(long userId) {
        long code = 100000 + secureRandom.nextInt(900000);
        String key = CODE_PREFIX + code;

        userRedisTemplate.opsForValue().set(key, userId, Duration.ofMinutes(CODE_EXPIRATION_MINUTES));
        return code;
    }
}
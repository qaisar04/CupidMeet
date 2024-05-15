package kz.baltabayev.authservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private Duration jwtLifeime;

    public String generateToken(long userId) {
        Claims claims = Jwts.claims()
                .subject(String.valueOf(userId))
                .build();

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifeime.toMillis());

        return Jwts.builder()
                .claims(claims)
                .issuedAt(issuedDate)
                .expiration(expiredDate)
                .signWith(signKey())
                .compact();
    }

    public Long getUserId(String token) {
        return Long.parseLong(extractAllClaims(token).getSubject());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(signKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey signKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

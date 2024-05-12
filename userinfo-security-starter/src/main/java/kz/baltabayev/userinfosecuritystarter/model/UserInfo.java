package kz.baltabayev.userinfosecuritystarter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A model class representing a user's information extracted from JWT claims.
 */
@Getter
@Slf4j
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo implements Serializable {

    @JsonProperty(value = "sub")
    private String userId;

    @JsonProperty(value = "authorities")
    private Set<String> authorities;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "last_name")
    private String lastName;

    /**
     * Converts a set of authority strings to a collection of GrantedAuthority objects.
     * Logs an error and initializes the authorities set if it is found to be null.
     * @return a collection of GrantedAuthority based on the authority strings.
     */
    public Collection<GrantedAuthority> getAuthorities() {
        if (authorities == null) {
            log.error("getAuthorities(): authorities is null!");
            authorities = new HashSet<>();
        }

        log.error("getAuthorities(): authorities is not null!");
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
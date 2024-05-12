package kz.baltabayev.userinfosecuritystarter.config;

import kz.baltabayev.userinfosecuritystarter.context.UserLoader;
import kz.baltabayev.userinfosecuritystarter.filter.UserInfoMappingFilter;
import kz.baltabayev.userinfosecuritystarter.parser.JwtParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Configuration class for setting up security-related components such as JWT parsing, user loading, and security filters.
 * This class uses Spring Security's configuration mechanisms to define beans and security filters that enforce authentication and authorization policies.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Creates a JwtParser bean used for parsing JWT tokens from HTTP requests.
     * @return JwtParser instance for processing JWT tokens.
     */
    @Bean
    public JwtParser jwtParser() {
        return new JwtParser();
    }

    /**
     * Creates a UserLoader bean that is responsible for managing user data within the security context.
     * @return UserLoader instance for managing user information.
     */
    @Bean
    public UserLoader userLoader() {
        return new UserLoader();
    }

    /**
     * Creates a UserInfoMappingFilter bean, configured with a JwtParser and a UserLoader.
     * The UserInfoMappingFilter intercepts HTTP requests to extract and validate user information from JWTs,
     * which is then used to populate the SecurityContext for subsequent security checks during the request handling.
     *
     * @param jwtParser the JwtParser bean used for parsing JWT tokens from HTTP requests.
     * @param userLoader the UserLoader bean used for managing user data within the security context.
     * @return UserInfoMappingFilter configured with the necessary dependencies to perform user information mapping.
     */
    @Bean
    public UserInfoMappingFilter userInfoMappingFilter(JwtParser jwtParser, UserLoader userLoader) {
        return new UserInfoMappingFilter(jwtParser, userLoader);
    }

    /**
     * Defines the security filter chain that enforces security policies on HTTP requests.
     * It disables CSRF and HTTP Basic authentication, adds a custom filter for mapping user info from JWTs,
     * and sets the session management policy to stateless.
     *
     * @param http the HttpSecurity context that provides methods to configure the web based security.
     * @return the configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterAfter(userInfoMappingFilter(jwtParser(), userLoader()), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(c -> c.anyRequest().permitAll())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
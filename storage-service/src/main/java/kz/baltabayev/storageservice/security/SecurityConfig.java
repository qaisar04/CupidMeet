package kz.baltabayev.storageservice.security;

import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
class SecurityConfig {

    /**
     * Документация.
     */
    private static final String[] SWAGGER_ENDPOINTS = {"/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**"};

    /**
     * Актуатор.
     */
    private static final String ACTUATOR_ENDPOINTS = "/actuator/**";

    /**
     * Роль пользователя приложения.
     */
    @Value("${application.authRoles}")
    private String editorAuthRole;

    @Value("${application.authRolesViewer}")
    private String viewerAuthRole;

    @Value("${application.cors.allowed-origins}")
    private List<String> allowedOrigins;

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = new KeycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(SWAGGER_ENDPOINTS).permitAll()
                        .requestMatchers(ACTUATOR_ENDPOINTS).permitAll()
//                        .requestMatchers(HttpMethod.GET).hasAnyAuthority(viewerAuthRole, editorAuthRole)
//                        .requestMatchers(HttpMethod.POST).hasAuthority(editorAuthRole)
//                        .requestMatchers(HttpMethod.PUT).hasAuthority(editorAuthRole)
//                        .requestMatchers(HttpMethod.PATCH).hasAuthority(editorAuthRole)
//                        .requestMatchers(HttpMethod.DELETE).hasAuthority(editorAuthRole)
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(
                        httpSecurityOAuth2ResourceServerConfigurer -> httpSecurityOAuth2ResourceServerConfigurer.jwt(
                                jwtConfigurer -> jwtConfigurer.jwkSetUri(jwkSetUri)
                        )
                )
                .cors(cors -> cors.configurationSource(request -> getCorsConfiguration()))
                .build();
    }

    private CorsConfiguration getCorsConfiguration() {
        CorsConfiguration cors = new CorsConfiguration();
        List<String> allowedAllValues = List.of("*");

        cors.setAllowedOrigins(allowedOrigins);
        cors.setAllowedMethods(allowedAllValues);
        cors.setExposedHeaders(allowedAllValues);
        cors.setAllowedHeaders(allowedAllValues);
        return cors;
    }
}

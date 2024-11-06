package io.github.venkat1701.yugantaarbackend.config.security;

import io.github.venkat1701.yugantaarbackend.utilities.security.jwt.JwtValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Security configuration class for the application.
 * <p>
 * This class is responsible for setting up the security filters, password encoding,
 * CORS configurations, and the security policies for HTTP requests.
 * It ensures that API endpoints are protected and that security best practices are followed.
 * </p>
 *
 * <p>
 * Author: Venkat
 * </p>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Configures the security filter chain.
     *
     * @param httpSecurity the HttpSecurity object to configure security settings
     * @return a configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsFilter())) // Configure CORS
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session management
                .authorizeHttpRequests(requests -> {
                    requests.anyRequest().permitAll(); // Allow all other requests
                })
                .addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class) // Add JWT validation filter
                .httpBasic(Customizer.withDefaults()) // Enable basic authentication
                .formLogin(Customizer.withDefaults()) // Enable form login
                .build();
    }

    /**
     * Provides a password encoder using BCrypt hashing algorithm.
     *
     * @return a PasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures CORS settings for the application.
     *
     * @return a CorsConfigurationSource to handle CORS requests
     */
    @Bean
    public CorsConfigurationSource corsFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // Allow all origins
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allow specified HTTP methods
        configuration.setAllowCredentials(true); // Allow credentials
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type")); // Expose headers
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type")); // Allow specified headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply configuration to all endpoints

        return source;
    }
}

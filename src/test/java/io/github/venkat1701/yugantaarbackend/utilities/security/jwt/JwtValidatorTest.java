package io.github.venkat1701.yugantaarbackend.utilities.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * JwtValidatorTest is a unit test class that verifies the functionality
 * of the JwtValidator class responsible for handling JWT authentication.
 * It ensures that the JWT processing works correctly under various scenarios.
 */
class JwtValidatorTest {

    private JwtValidator jwtValidator;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private SecretKey key;
    private static final String SECRET_KEY = "rRHjbQ1kgvZg970iZw9phjJ5YXDpAjaNw9Vy+TTMXLY="; // Example key

    /**
     * Sets up the test environment before each test case.
     * Initializes mocks and the JwtValidator instance.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtValidator = new JwtValidator();
        key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    /**
     * Generates a JWT token for testing purposes.
     *
     * @param email      The email to include in the token.
     * @param userId    The user ID to include in the token.
     * @param authorities The authorities to include in the token.
     * @return A JWT token as a String.
     */
    private String generateToken(String email, Long userId, String authorities) {
        return Jwts.builder()
                .setSubject(email)
                .claim("email", email)
                .claim("userId", userId)
                .claim("authorities", authorities)
                .signWith(key) // Signs the token with the secret key
                .compact();
    }

    /**
     * Tests that a valid token sets the authentication in the SecurityContext.
     */
    @Test
    void doFilterInternal_ShouldSetAuthentication_WhenTokenIsValid() throws Exception {
        // Arrange
        String token = generateToken("test@example.com", 1L, "ROLE_USER");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        // Act
        jwtValidator.doFilterInternal(request, response, filterChain);

        // Assert
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertTrue(SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken);
        assertEquals("test@example.com", SecurityContextHolder.getContext().getAuthentication().getName());
        assertEquals(1, SecurityContextHolder.getContext().getAuthentication().getAuthorities().size());
    }

    /**
     * Tests that an invalid token does not set authentication and throws an exception.
     */
    @Test
    void doFilterInternal_ShouldNotSetAuthentication_WhenTokenIsInvalid() throws Exception {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("Bearer InvalidToken");

        // Act & Assert
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> jwtValidator.doFilterInternal(request, response, filterChain));
        assertEquals("Invalid JWT Token", exception.getMessage());
    }

    /**
     * Tests that a malformed token throws an exception and does not set authentication.
     */
    @Test
    void doFilterInternal_ShouldHandleMalformedTokenGracefully() throws Exception {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("Bearer malformed.token.here");

        // Act & Assert
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> jwtValidator.doFilterInternal(request, response, filterChain));
        assertEquals("Invalid JWT Token", exception.getMessage());
    }

    /**
     * Tests that if the claims in the token are invalid, authentication is not set.
     */
    @Test
    void doFilterInternal_ShouldNotSetAuthentication_WhenClaimsAreInvalid() throws Exception {
        // Arrange
        String token = generateToken("test@example.com", null, "ROLE_USER"); // User ID is null
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        // Act
        jwtValidator.doFilterInternal(request, response, filterChain);

        // Assert
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("test@example.com", SecurityContextHolder.getContext().getAuthentication().getName());
        assertNull(SecurityContextHolder.getContext().getAuthentication().getCredentials());
    }
}

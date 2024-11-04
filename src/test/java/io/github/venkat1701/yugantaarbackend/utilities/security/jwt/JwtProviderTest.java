package io.github.venkat1701.yugantaarbackend.utilities.security.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JwtProviderTest is a unit test class that verifies the functionality
 * of the JwtProvider class responsible for creating and validating JWT tokens.
 * It ensures that token generation and extraction of email from tokens work correctly.
 */
class JwtProviderTest {

    private JwtProvider jwtProvider;

    /**
     * Sets up the test environment before each test case.
     * Initializes the JwtProvider instance.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtProvider = new JwtProvider();
    }

    /**
     * Tests that a valid input generates a JWT token.
     */
    @Test
    void generateToken_ShouldReturnToken_WhenValidInput() {
        // Arrange
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("test@example.com", null);
        Long userId = 1L;

        // Act
        String token = jwtProvider.generateToken(auth, userId);

        // Assert
        assertNotNull(token);
        assertTrue(token.startsWith("ey")); // Valid JWT tokens start with "ey"
    }

    /**
     * Tests that a valid token returns the correct email.
     */
    @Test
    void getEmailFromToken_ShouldReturnEmail_WhenTokenIsValid() {
        // Arrange
        String token = jwtProvider.generateToken(new UsernamePasswordAuthenticationToken("test@example.com", null), 1L);

        // Act
        String email = jwtProvider.getEmailFromToken(token);

        // Assert
        assertEquals("test@example.com", email);
    }

    /**
     * Tests that an invalid token throws a BadCredentialsException.
     */
    @Test
    void getEmailFromToken_ShouldThrowException_WhenTokenIsInvalid() {
        // Arrange
        String invalidToken = "InvalidToken";

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> jwtProvider.getEmailFromToken(invalidToken));
    }
}

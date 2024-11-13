package io.github.venkat1701.yugantaarbackend.utilities.security.jwt;

import io.github.venkat1701.yugantaarbackend.models.roles.Role;
import io.github.venkat1701.yugantaarbackend.models.users.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JWT Provider service for generating and validating JSON Web Tokens (JWT).
 * <p>
 * This class is responsible for creating JWT tokens for authenticated users
 * and for extracting information from those tokens, such as the user's email.
 * It ensures that the tokens are signed and secured using a secret key.
 * </p>
 *
 * <p>
 * Author: Venkat
 * </p>
 */
@Service
public class JwtProvider {
    private static final int EXPIRATION_TIME = 864000000; // Token expiration time in milliseconds (10 days)
    private static final String TOKEN_PREFIX = "Bearer "; // Prefix for the JWT token

    /**
     * Generates a JWT token for a given authenticated user.
     *
     * @param authentication the authentication object containing user details
     * @param userId the unique identifier of the user
     * @return a signed JWT token as a String
     */
    public String generateToken(Authentication authentication, Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setIssuedAt(now) // Set the issue date of the token
                .setExpiration(expiryDate) // Set the expiration date of the token
                .claim("email", authentication.getName()) // Include the user's email as a claim
                .claim("userId", userId) // Include the user's ID as a claim
                .signWith(Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes())) // Sign the token with the secret key
                .compact(); // Build the token
    }

    /**
     * Extracts the email address from a given JWT token.
     *
     * @param jwt the JWT token as a String
     * @return the email address contained in the token
     * @throws BadCredentialsException if the token is invalid or cannot be parsed
     */
    public String getEmailFromToken(String jwt) {
        try {
            if (jwt.startsWith(TOKEN_PREFIX)) {
                jwt = jwt.substring(TOKEN_PREFIX.length()); // Remove the Bearer prefix if present
            }

            SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key) // Set the signing key for validation
                    .build()
                    .parseClaimsJws(jwt) // Parse the JWT and retrieve claims
                    .getBody();

            return claims.get("email", String.class); // Return the email from claims
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid JWT Token"); // Handle invalid tokens
        }
    }

    public String generateToken(User user) {
        SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());

        // Extract roles
        List<String> roles = user.getRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());

        // Extract permissions
        List<String> permissions = user.getRoles().stream()
                .flatMap(role -> role.getRolePermissions().stream())
                .map(permission -> permission.getPermission().getPermissionName())
                .collect(Collectors.toList());

        String token = Jwts.builder()
                .setSubject("Yugantaar JWT Token")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 hours
                .claim("email", user.getEmail())
                .claim("userId", user.getId())
                .claim("roles", roles)
                .claim("permissions", permissions)
                .signWith(key)
                .compact();

        return token;
    }
}

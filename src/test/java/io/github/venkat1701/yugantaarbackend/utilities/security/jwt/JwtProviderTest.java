package io.github.venkat1701.yugantaarbackend.utilities.security.jwt;

import io.github.venkat1701.yugantaarbackend.models.permissions.Permission;
import io.github.venkat1701.yugantaarbackend.models.roles.Role;
import io.github.venkat1701.yugantaarbackend.models.roles.RolePermission;
import io.github.venkat1701.yugantaarbackend.models.users.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtProviderTest {

    @InjectMocks
    private JwtProvider jwtProvider;

    private User testUser;

    @BeforeEach
    void setUp() {
        // Create test permissions
        Permission readPermission = new Permission();
        readPermission.setId(1L);
        readPermission.setPermissionName("READ_USER");

        Permission writePermission = new Permission();
        writePermission.setId(2L);
        writePermission.setPermissionName("WRITE_USER");

        // Create role permissions
        RolePermission readRolePermission = new RolePermission();
        readRolePermission.setPermission(readPermission);

        RolePermission writeRolePermission = new RolePermission();
        writeRolePermission.setPermission(writePermission);

        Set<RolePermission> rolePermissions = new HashSet<>();
        rolePermissions.add(readRolePermission);
        rolePermissions.add(writeRolePermission);

        // Create role
        Role adminRole = new Role();
        adminRole.setId(1L);
        adminRole.setRoleName("ADMIN");
        adminRole.setRolePermissions(rolePermissions);

        // Create user
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setRoles(Set.of(adminRole));
    }

    @Test
    void generateToken_ShouldReturnValidToken_WhenGivenUser() {
        // Act
        String token = jwtProvider.generateToken(testUser);

        // Assert
        assertNotNull(token);
        assertTrue(token.startsWith("ey")); // JWT format validation

        // Decode and verify token contents
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        // Verify standard claims
        assertEquals(testUser.getEmail(), claims.get("email"));
        assertEquals(testUser.getId(), claims.get("userId", Long.class));
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());

        // Verify roles
        List<String> roles = claims.get("roles", List.class);
        assertNotNull(roles);
        assertTrue(roles.contains("ADMIN"));

        // Verify permissions
        List<String> permissions = claims.get("permissions", List.class);
        assertNotNull(permissions);
        assertTrue(permissions.contains("READ_USER"));
        assertTrue(permissions.contains("WRITE_USER"));
    }

    @Test
    void getEmailFromToken_ShouldReturnEmail_WhenValidToken() {
        // Arrange
        String token = jwtProvider.generateToken(testUser);

        // Act
        String email = jwtProvider.getEmailFromToken(token);

        // Assert
        assertEquals(testUser.getEmail(), email);
    }

    @Test
    void generateToken_ShouldIncludeAllRoles_WhenUserHasMultipleRoles() {
        // Arrange
        Role guestRole = new Role();
        guestRole.setId(2L);
        guestRole.setRoleName("GUEST");
        guestRole.setRolePermissions(new HashSet<>());

        testUser.setRoles(Set.of(guestRole));

        // Act
        String token = jwtProvider.generateToken(testUser);

        // Decode and verify
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<String> roles = claims.get("roles", List.class);

        // Assert
        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertTrue(roles.contains("GUEST"));
    }

    @Test
    void generateToken_ShouldHaveValidExpiration() {
        // Act
        String token = jwtProvider.generateToken(testUser);

        // Decode and verify
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        // Assert
        assertNotNull(claims.getExpiration());
        assertTrue(claims.getExpiration().after(claims.getIssuedAt()));
        // Verify token expires in 24 hours (with 1 second tolerance)
        long expectedExpiration = claims.getIssuedAt().getTime() + 86400000;
        long actualExpiration = claims.getExpiration().getTime();
        assertTrue(Math.abs(expectedExpiration - actualExpiration) < 1000);
    }
}
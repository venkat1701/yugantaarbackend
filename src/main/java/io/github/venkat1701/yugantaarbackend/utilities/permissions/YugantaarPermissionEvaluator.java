package io.github.venkat1701.yugantaarbackend.utilities.permissions;

import io.github.venkat1701.yugantaarbackend.utilities.authorities.YugantaarGrantedAuthority;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

/**
 * Custom implementation of the {@link PermissionEvaluator} interface to provide
 * permission-based access control within the Yugantaar application.
 *
 * <p>This class evaluates whether a user has the necessary permissions or roles
 * to access a specific target domain object or perform an action, based on their
 * granted authorities.</p>
 *
 * <p>Author: Venkat</p>
 */
public class YugantaarPermissionEvaluator implements PermissionEvaluator {

    /**
     * Evaluates if the current {@link Authentication} object has the required permission.
     *
     * @param authentication the {@link Authentication} object representing the current user.
     * @param targetDomainObject the domain object being secured (can be ignored in this implementation).
     * @param permission the permission required as a {@link String}.
     * @return {@code true} if the user has the required permission or is a super admin; {@code false} otherwise.
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null || !(permission instanceof String)) {
            return false;
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String requiredPerms = (String) permission;

        System.out.println("AUTHORITIES: "+authentication.getAuthorities());
        // Check if the user has the required permission or is a super admin.
        return authorities.stream()
                .filter(auth -> auth instanceof YugantaarGrantedAuthority)
                .map(auth -> (YugantaarGrantedAuthority) auth)
                .anyMatch(auth ->
                        (auth.getType().equals("PERMISSION") && auth.getAuthority().equals(requiredPerms)) ||
                                (auth.getType().equals("ROLE") && auth.getAuthority().equals("ROLE_SUPERADMIN"))
                );
    }

    /**
     * Evaluates if the current {@link Authentication} object has the required permission
     * for a specific target identified by ID and type.
     *
     * @param authentication the {@link Authentication} object representing the current user.
     * @param targetId the ID of the target domain object (not used in this implementation).
     * @param targetType the type of the target domain object (can be passed as context).
     * @param permission the permission required as a {@link String}.
     * @return {@code true} if the user has the required permission; {@code false} otherwise.
     */
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        // Delegate to the main permission check method.
        return hasPermission(authentication, targetType, permission);
    }
}

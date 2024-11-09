package io.github.venkat1701.yugantaarbackend.utilities.userdetails;

import io.github.venkat1701.yugantaarbackend.models.users.User;
import io.github.venkat1701.yugantaarbackend.utilities.authorities.YugantaarGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * Implementation of the {@link UserDetails} interface that represents a user's
 * authentication and authorization details within the Yugantaar application.
 * This class provides user-specific data for Spring Security's authentication mechanisms.
 *
 * <p>Instances of this class are created based on a {@link User} object and
 * include the user's roles and permissions as granted authorities.</p>
 *
 * @author: Venkat
 */
public class YugantaarUserDetails implements UserDetails {

    /**
     * The {@link User} instance representing the current user.
     */
    private final User user;

    /**
     * A set of {@link GrantedAuthority} representing the roles and permissions
     * of the current user.
     */
    private final Set<GrantedAuthority> authorities;

    /**
     * Constructs a {@code YugantaarUserDetails} instance based on a {@link User} object.
     * Initializes the set of granted authorities by mapping the user's roles and
     * permissions.
     *
     * @param user the {@link User} instance representing the user for which the
     *             details are being created.
     */
    public YugantaarUserDetails(User user) {
        this.user = user;
        this.authorities = new HashSet<>();

        // Populate authorities based on the user's roles and permissions.
        Optional.ofNullable(user.getRoles())
                .ifPresent(roles -> roles.forEach(role -> {
                    // Add the role as a granted authority.
                    authorities.add(new YugantaarGrantedAuthority("ROLE_" + role.getRoleName(), "ROLE"));

                    // Add each permission as a granted authority.
                    role.getRolePermissions().stream()
                            .map(permission -> new YugantaarGrantedAuthority(
                                    permission.getPermission().getPermissionName(),
                                    "PERMISSION"
                            ))
                            .forEach(authorities::add);
                }));
    }

    /**
     * Returns the set of granted authorities assigned to the user.
     *
     * @return a collection of {@link GrantedAuthority} representing the user's
     * roles and permissions.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableSet(authorities);
    }

    /**
     * Returns the user's password.
     *
     * @return a {@code String} representing the user's password.
     */
    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    /**
     * Returns the username used for authentication.
     *
     * @return a {@code String} representing the user's username.
     */
    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    /**
     * Returns the email address associated with the user.
     *
     * @return a {@code String} representing the user's email.
     */
    public String getEmail() {
        return this.user.getEmail();
    }

    /**
     * Returns the unique identifier of the user.
     *
     * @return a {@code Long} representing the user's ID.
     */
    public Long getId() {
        return this.user.getId();
    }

    /**
     * Returns the {@link User} instance that this {@code UserDetails} is based on.
     *
     * @return the {@link User} instance.
     */
    public User getUser() {
        return user;
    }

    // These methods are mandatory overrides for the UserDetails interface.

    /**
     * Indicates whether the user's account has expired.
     * Always returns {@code true} as account expiration is not being managed.
     *
     * @return {@code true} (account is non-expired).
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is locked.
     * Always returns {@code true} as account locking is not being managed.
     *
     * @return {@code true} (account is non-locked).
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (e.g., password) have expired.
     * Always returns {@code true} as credential expiration is not being managed.
     *
     * @return {@code true} (credentials are non-expired).
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is enabled.
     * Always returns {@code true} as account enabling is not being managed.
     *
     * @return {@code true} (account is enabled).
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}

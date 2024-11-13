package io.github.venkat1701.yugantaarbackend.utilities.authorities;

import org.springframework.security.core.GrantedAuthority;

/**
 * Custom implementation of the {@link GrantedAuthority} interface used to represent
 * both roles and permissions in the Yugantaar application.
 *
 * <p>This class extends Spring Security's authorization model by including an additional
 * type field to distinguish between different types of authorities (e.g., roles or permissions).</p>
 *
 * <p>Author: Venkat</p>
 */
public class YugantaarGrantedAuthority implements GrantedAuthority {

    /**
     * The authority value (e.g., "ROLE_ADMIN" or "READ_PRIVILEGE").
     */
    private final String authority;

    /**
     * The type of the authority, which distinguishes whether it is a role or a permission.
     * For example, the type could be "ROLE" or "PERMISSION".
     */
    private final String type;

    /**
     * Constructs a new {@code YugantaarGrantedAuthority} with the given authority and type.
     *
     * @param authority the authority value as a {@link String}, typically prefixed for roles (e.g., "ROLE_USER").
     * @param type the type of the authority as a {@link String} (e.g., "ROLE" or "PERMISSION").
     */
    public YugantaarGrantedAuthority(String authority, String type) {
        this.authority = authority;
        this.type = type;

        System.out.println("Authority: "+this.authority);
        System.out.println("Type: "+this.type);
    }

    /**
     * Returns the authority value.
     *
     * @return the authority as a {@link String}.
     */
    @Override
    public String getAuthority() {
        return this.authority;
    }

    /**
     * Returns the type of the authority.
     *
     * @return the type of the authority as a {@link String}.
     */
    public String getType() {
        return this.type;
    }
}

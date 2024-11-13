package io.github.venkat1701.yugantaarbackend.utilities.permissions.authannotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
public @interface RequiresRoleCreatePermission {
}

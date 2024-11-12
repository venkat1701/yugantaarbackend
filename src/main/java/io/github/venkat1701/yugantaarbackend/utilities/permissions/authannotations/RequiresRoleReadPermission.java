package io.github.venkat1701.yugantaarbackend.utilities.permissions.authannotations;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_GUEST','ROLE_SUPERADMIN')")
public @interface RequiresRoleReadPermission {
}

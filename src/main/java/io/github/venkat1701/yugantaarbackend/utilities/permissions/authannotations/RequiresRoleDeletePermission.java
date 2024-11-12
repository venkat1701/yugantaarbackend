package io.github.venkat1701.yugantaarbackend.utilities.permissions.authannotations;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyAuthority('ROLE_SUPERADMIN')")
public @interface RequiresRoleDeletePermission {
}

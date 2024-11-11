package io.github.venkat1701.yugantaarbackend.utilities.permissions.authannotations;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyRole('SUPERADMIN',' PARTICIPANT',' MANAGER',' ADMIN')")
public @interface RequiresUserDeletePermission {
}

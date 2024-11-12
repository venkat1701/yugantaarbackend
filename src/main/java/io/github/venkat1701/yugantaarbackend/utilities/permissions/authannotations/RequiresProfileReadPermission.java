package io.github.venkat1701.yugantaarbackend.utilities.permissions.authannotations;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyRole('GUEST',' PARTICIPANT',' MANAGER',' SUPERADMIN',' ADMIN')")
public @interface RequiresProfileReadPermission {
}

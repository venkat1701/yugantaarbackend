package io.github.venkat1701.yugantaarbackend.utilities.permissions.authannotations;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyRole('ADMIN',' MANAGER',' SUPERADMIN')")
public @interface RequiresVenueListPermission {
}

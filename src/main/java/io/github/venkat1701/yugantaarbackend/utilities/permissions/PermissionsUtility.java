package io.github.venkat1701.yugantaarbackend.utilities.permissions;

import io.github.venkat1701.yugantaarbackend.models.roles.RolesEnum;
import org.springframework.stereotype.Component;

@Component("permissionsUtility")
public class PermissionsUtility {

    public static String[] getRolesForPermission(PermissionsEnum permission) {
        return permission.getPermissibleRoles().stream()
                .map(RolesEnum::name)
                .toArray(String[]::new);
    }
}

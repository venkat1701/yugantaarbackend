package io.github.venkat1701.yugantaarbackend.utilities.permissions;

import io.github.venkat1701.yugantaarbackend.models.roles.RolesEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Configuration("permissionsUtility")
public class PermissionsUtility {

    public static String[] getRolesForPermission(PermissionsEnum permission) {
        var roles =  permission.getPermissibleRoles().stream()
                .map(RolesEnum::name)
                .toArray(String[]::new);

        Arrays.stream(roles).forEach(System.out::println);
        return roles;
    }
}

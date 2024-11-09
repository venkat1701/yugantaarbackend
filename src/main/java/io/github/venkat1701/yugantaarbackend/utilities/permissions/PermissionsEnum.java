package io.github.venkat1701.yugantaarbackend.utilities.permissions;

import io.github.venkat1701.yugantaarbackend.models.roles.Role;
import io.github.venkat1701.yugantaarbackend.models.roles.RolesEnum;
import static io.github.venkat1701.yugantaarbackend.models.roles.RolesEnum.*;
import java.util.HashSet;
import java.util.Set;

public enum PermissionsEnum {
    USER_CREATE("Creates User", Set.of(GUEST, PARTICIPANT, MANAGER, ADMIN, SUPERADMIN)),
    USER_READ("Reads User", Set.of(GUEST, PARTICIPANT, MANAGER, ADMIN, SUPERADMIN)),
    USER_UPDATE("Updates User", Set.of(PARTICIPANT, MANAGER, ADMIN, SUPERADMIN)),
    USER_DELETE("Deletes User", Set.of(PARTICIPANT, MANAGER, ADMIN, SUPERADMIN)),
    USER_LIST("Lists User", Set.of(MANAGER, ADMIN, SUPERADMIN)),

    PROFILE_READ("Reads Profile", Set.of(GUEST, PARTICIPANT, MANAGER, ADMIN, SUPERADMIN)),
    PROFILE_UPDATE_OWN("Updates Own Profile", Set.of(PARTICIPANT, MANAGER, ADMIN, SUPERADMIN)),
    PROFILE_UPDATE_ANY("Updates Any Profile", Set.of(MANAGER, ADMIN, SUPERADMIN)),

    REGISTRATION_CREATE("Creates Registration", Set.of(GUEST, PARTICIPANT, MANAGER, ADMIN, SUPERADMIN)),
    REGISTRATION_READ_OWN("Reads Own Registration", Set.of(PARTICIPANT, MANAGER, ADMIN, SUPERADMIN)),
    REGISTRATION_UPDATE_OWN("Updates Own Registration", Set.of(PARTICIPANT, MANAGER, ADMIN, SUPERADMIN)),
    REGISTRATION_UPDATE_ANY("Updates Any Registration", Set.of(MANAGER, ADMIN, SUPERADMIN)),
    REGISTRATION_DELETE("Deletes Registration", Set.of(PARTICIPANT, MANAGER, ADMIN, SUPERADMIN)),
    REGISTRATION_LIST("Lists Registration", Set.of(MANAGER, ADMIN, SUPERADMIN)),

    PAYMENT_CREATE("Creates Payment", Set.of(GUEST, PARTICIPANT, MANAGER, ADMIN, SUPERADMIN)),
    PAYMENT_READ_OWN("Reads Own Payment", Set.of(PARTICIPANT, MANAGER, ADMIN, SUPERADMIN)),
    PAYMENT_READ_ANY("Reads Any Payment", Set.of(MANAGER, ADMIN, SUPERADMIN)),
    PAYMENT_UPDATE("Updates Payment", Set.of(ADMIN, SUPERADMIN)),
    PAYMENT_DELETE("Deletes Payment", Set.of(SUPERADMIN)),
    PAYMENT_REFUND("Process Refunds", Set.of(ADMIN, SUPERADMIN)),
    PAYMENT_LIST("Lists Payment", Set.of(MANAGER, ADMIN, SUPERADMIN)),

    EVENT_CREATE("Creates Event", Set.of(MANAGER, ADMIN, SUPERADMIN)),
    EVENT_READ("Reads Event", Set.of(GUEST, PARTICIPANT, MANAGER, ADMIN, SUPERADMIN)),
    EVENT_UPDATE("Updates Event", Set.of(MANAGER, ADMIN, SUPERADMIN)),
    EVENT_DELETE("Deletes Event", Set.of(MANAGER, ADMIN, SUPERADMIN)),
    EVENT_LIST("Lists Event", Set.of(MANAGER, ADMIN, SUPERADMIN)),

    ROLE_CREATE("Creates Role", Set.of(SUPERADMIN)),
    ROLE_READ("Reads Role", Set.of(ADMIN, SUPERADMIN)),
    ROLE_UPDATE("Updates Role", Set.of(SUPERADMIN)),
    ROLE_DELETE("Deletes Role", Set.of(SUPERADMIN)),
    ROLE_LIST("Lists Role", Set.of(SUPERADMIN)),
    PERMISSION_ASSIGN("Assigns Permission", Set.of(SUPERADMIN)),
    PERMISSION_REVOKE("Revokes Permission", Set.of(SUPERADMIN)),

    REPORT_GENERATE("Generates Report", Set.of(MANAGER, ADMIN, SUPERADMIN));



    private String description;
    private Set<RolesEnum> permissibleRoles = new HashSet<>();

    PermissionsEnum(String description, Set<RolesEnum> permissibleRoles) {
        this.description = description;
        this.permissibleRoles = permissibleRoles;
    }

    public String getDescription() {
        return description;
    }

    public Set<RolesEnum> getPermissibleRoles() {
        return permissibleRoles;
    }
}

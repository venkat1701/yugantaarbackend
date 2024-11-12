package io.github.venkat1701.yugantaarbackend.services.core.roles;

import io.github.venkat1701.yugantaarbackend.dto.roles.RoleDTO;
import io.github.venkat1701.yugantaarbackend.models.roles.Role;
import io.github.venkat1701.yugantaarbackend.services.core.commons.GenericCrudService;
import org.springframework.stereotype.Service;

@Service
public interface RoleService extends GenericCrudService<Role, Long> {

    Role registerRole(RoleDTO roleDTO);

}

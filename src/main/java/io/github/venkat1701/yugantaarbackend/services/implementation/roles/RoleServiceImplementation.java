package io.github.venkat1701.yugantaarbackend.services.implementation.roles;

import io.github.venkat1701.yugantaarbackend.dto.roles.RoleDTO;
import io.github.venkat1701.yugantaarbackend.models.roles.Role;
import io.github.venkat1701.yugantaarbackend.repositories.roles.RoleRepository;
import io.github.venkat1701.yugantaarbackend.services.core.roles.RoleService;
import io.github.venkat1701.yugantaarbackend.utilities.permissions.authannotations.RequiresRoleCreatePermission;
import io.github.venkat1701.yugantaarbackend.utilities.permissions.authannotations.RequiresRoleDeletePermission;
import io.github.venkat1701.yugantaarbackend.utilities.permissions.authannotations.RequiresRoleReadPermission;
import io.github.venkat1701.yugantaarbackend.utilities.permissions.authannotations.RequiresRoleUpdatePermission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImplementation implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImplementation(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Retrieves a paginated list of roles based on the provided `PageRequest`.
     *
     * @param pageRequest the `PageRequest` object containing the pagination and sorting information
     * @return a `Page` of roles
     */
    @Override
    public Page<Role> search(PageRequest pageRequest) {
        return this.roleRepository.findAll(pageRequest);
    }

    @Override
    @RequiresRoleReadPermission
    public List<Role> getAll() {
        return this.roleRepository.findAll();
    }

    @Override
    @RequiresRoleReadPermission
    public Optional<Role> findById(Long aLong) {
        return this.roleRepository.findById(aLong);
    }

    @Override
    @RequiresRoleUpdatePermission
    public Optional<Role> update(Long aLong, Role role) {
        if(this.roleRepository.existsById(aLong)) {
            return Optional.of(this.roleRepository.save(role));
        }
        return Optional.empty();
    }

    @Override
    @RequiresRoleDeletePermission
    public boolean delete(Long aLong) {
        if(this.roleRepository.existsById(aLong)) {
            this.roleRepository.deleteById(aLong);
            return true;
        }
        return false;
    }


    @Override
    @RequiresRoleCreatePermission
    public Role registerRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setRoleName(roleDTO.getRoleName());
        role.setDescription(roleDTO.getDescription());

        return this.roleRepository.save(role);
    }
}

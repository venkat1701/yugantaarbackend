package io.github.venkat1701.yugantaarbackend.services.implementation.roles;

import io.github.venkat1701.yugantaarbackend.dto.roles.RoleDTO;
import io.github.venkat1701.yugantaarbackend.models.roles.Role;
import io.github.venkat1701.yugantaarbackend.repositories.roles.RoleRepository;
import io.github.venkat1701.yugantaarbackend.services.core.roles.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImplementation implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImplementation(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    private boolean isSuperAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
                authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_SUPERADMIN"));
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
    public Page<Role> search(PageRequest pageRequest) {
        if (!isSuperAdmin()) {
            throw new SecurityException("Access denied: Requires ROLE_SUPERADMIN");
        }
        return this.roleRepository.findAll(pageRequest);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_SUPERADMIN', 'ROLE_GUEST')")
    public List<Role> getAll() {
        return this.roleRepository.findAll();
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
    public Optional<Role> findById(Long id) {
        if (!isSuperAdmin()) {
            throw new SecurityException("Access denied: Requires ROLE_SUPERADMIN");
        }
        return this.roleRepository.findById(id);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
    public Optional<Role> update(Long id, Role role) {
        if (!isSuperAdmin()) {
            throw new SecurityException("Access denied: Requires ROLE_SUPERADMIN");
        }
        if (this.roleRepository.existsById(id)) {
            role.setId(id);
            return Optional.of(this.roleRepository.save(role));
        }
        return Optional.empty();
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
    public boolean delete(Long id) {
        if (!isSuperAdmin()) {
            throw new SecurityException("Access denied: Requires ROLE_SUPERADMIN");
        }
        if (this.roleRepository.existsById(id)) {
            this.roleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
    public Role registerRole(RoleDTO roleDTO) {
        if (!isSuperAdmin()) {
            throw new SecurityException("Access denied: Requires ROLE_SUPERADMIN");
        }
        Role role = new Role();
        role.setRoleName(roleDTO.getRoleName());
        role.setDescription(roleDTO.getDescription());

        return this.roleRepository.save(role);
    }
}
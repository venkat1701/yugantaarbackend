package io.github.venkat1701.yugantaarbackend.repositories.roles;

import io.github.venkat1701.yugantaarbackend.models.roles.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);
}

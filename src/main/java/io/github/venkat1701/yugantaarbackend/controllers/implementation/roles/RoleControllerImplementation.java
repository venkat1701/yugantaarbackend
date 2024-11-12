package io.github.venkat1701.yugantaarbackend.controllers.implementation.roles;

import io.github.venkat1701.yugantaarbackend.controllers.core.roles.RolesController;
import io.github.venkat1701.yugantaarbackend.dto.roles.RoleDTO;
import io.github.venkat1701.yugantaarbackend.models.roles.Role;
import io.github.venkat1701.yugantaarbackend.services.core.roles.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleControllerImplementation implements RolesController<Role, RoleDTO, Long> {
    
    private final RoleService roleService;
    
    public RoleControllerImplementation(RoleService roleService) {
        this.roleService = roleService;
    }
    
    @Override
    @PostMapping("/create")
    public ResponseEntity<Role> create(@RequestBody RoleDTO roleEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.roleService.registerRole(roleEntity));
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<Role>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.roleService.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Role> getById(@PathVariable Long id) {
        if(this.roleService.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(this.roleService.findById(id).get());
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Role> update(@PathVariable Long id,@RequestBody Role entity) {
        if(this.roleService.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(this.roleService.update(id, entity).get());
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(this.roleService.findById(id).isPresent()) {
            this.roleService.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<Role>> search(@RequestParam int page,@RequestParam int size,@RequestParam String sort) {
        PageRequest pageRequest;
        if(sort!=null && !sort.isEmpty()) {
            String[] sortParameters = sort.split(",");
            Sort.Direction direction = sortParameters.length > 1?
                    Sort.Direction.fromString(sortParameters[1]) : Sort.Direction.ASC;
            pageRequest = PageRequest.of(page, size, Sort.by(direction, sortParameters));
        } else {
            pageRequest = PageRequest.of(page, size);
        }

        Page<Role> roles = this.roleService.search(pageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }
}

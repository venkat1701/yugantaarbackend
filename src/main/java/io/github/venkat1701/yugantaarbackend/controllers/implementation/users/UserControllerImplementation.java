package io.github.venkat1701.yugantaarbackend.controllers.implementation.users;

import io.github.venkat1701.yugantaarbackend.controllers.core.users.UserController;

import io.github.venkat1701.yugantaarbackend.dto.users.auth.GuestSignupDTO;
import io.github.venkat1701.yugantaarbackend.models.users.User;
import io.github.venkat1701.yugantaarbackend.services.core.users.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController()
@RequestMapping("/api/v1/users")
public class UserControllerImplementation implements UserController<User, GuestSignupDTO, Long> {

    private final UserService userService;

    public UserControllerImplementation(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    @Override
    public ResponseEntity<User> create(@RequestBody GuestSignupDTO entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.registerUser(entity));
    }

    @GetMapping("/all")
    @Override
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getAll());
    }

    /**
     * Retrieves a User resource by its id.
     *
     * @param id the id of the User to retrieve
     *
     * @return ResponseEntity containing the User object if found, else NOT_FOUND.
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.findById(id).get());
    }

    /**
     * Updates an existing User resource based on the id and updates User object provided.
     *
     * @param id the id of the User to update
     * @param entity the User object containing the updated data
     *
     * @return ResponseEntity containing the updated User object if found, else NOT_FOUND.
     */
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id,@RequestBody User entity) {
        if(this.userService.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(this.userService.update(id, entity).get());
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(this.userService.delete(id))
            return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    @Override
    public ResponseEntity<Page<User>> search(@RequestParam int page,@RequestParam int size,@RequestParam String sort) {
        PageRequest pageRequest;
        if(sort!=null && !sort.isEmpty()) {
            String[] sortParameters = sort.split(",");
            Sort.Direction direction = sortParameters.length > 1?
                    Sort.Direction.fromString(sortParameters[1]) : Sort.Direction.ASC;
            pageRequest = PageRequest.of(page, size, Sort.by(direction, sortParameters[0]));
        } else {
            pageRequest = PageRequest.of(page, size);
        }

        Page<User> users = this.userService.search(pageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}

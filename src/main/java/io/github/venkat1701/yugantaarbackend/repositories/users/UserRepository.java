package io.github.venkat1701.yugantaarbackend.repositories.users;

import io.github.venkat1701.yugantaarbackend.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

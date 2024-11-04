package io.github.venkat1701.yugantaarbackend.services.users.implementation;

import io.github.venkat1701.yugantaarbackend.dto.users.UserRegistrationDTO;
import io.github.venkat1701.yugantaarbackend.models.roles.Role;
import io.github.venkat1701.yugantaarbackend.models.roles.RolesEnum;
import io.github.venkat1701.yugantaarbackend.models.users.User;
import io.github.venkat1701.yugantaarbackend.repositories.roles.RoleRepository;
import io.github.venkat1701.yugantaarbackend.repositories.users.UserRepository;
import io.github.venkat1701.yugantaarbackend.services.users.core.UserService;
import io.github.venkat1701.yugantaarbackend.utilities.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implementation of the UserService interface for managing user-related operations.
 * <p>
 * This service provides functionality to load users by username, register new users,
 * create administrator accounts, and retrieve user profiles based on JWT tokens.
 * It integrates with repositories for data access and utilizes Spring Security for authentication.
 * </p>
 *
 * <p>
 * Author: Venkat
 * </p>
 */
@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    /**
     * Constructs a UserServiceImplementation with the necessary dependencies.
     *
     * @param userRepository   the repository for user data access
     * @param jwtProvider      the provider for JWT operations
     * @param passwordEncoder  the encoder for user passwords
     * @param roleRepository   the repository for role data access
     */
    @Autowired
    public UserServiceImplementation(UserRepository userRepository, JwtProvider jwtProvider, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    /**
     * Loads a user by their username (email) for authentication purposes.
     *
     * @param username the email of the user to be loaded
     * @return a UserDetails object representing the authenticated user
     * @throws UsernameNotFoundException if no user is found with the given username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username));

            Set<Role> roles = user.getRoles();
            List<SimpleGrantedAuthority> listAuthorities = new ArrayList<>();
            for (Role role : roles) {
                listAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
            }
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), listAuthorities);
        } catch (UsernameNotFoundException unfe) {
            unfe.printStackTrace();
            // Improvement in logging required. To be added after [YUGA-0003]
            return null;
        }
    }

    /**
     * Retrieves the authorities granted to the current user.
     *
     * @return a collection of GrantedAuthority for the current user
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // Currently returns an empty list of authorities
    }

    /**
     * Finds the user profile associated with the provided JWT token.
     *
     * @param jwt the JSON Web Token used to identify the user
     * @return the User object corresponding to the JWT
     * @throws Exception if the JWT is invalid or the user cannot be found
     */
    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        return user;
    }

    /**
     * Registers a new user based on the provided registration data.
     *
     * @param userRegistrationDTO the data transfer object containing user registration details
     * @return the newly registered User object, or null if the role is not found
     */
    @Override
    public User registerUser(UserRegistrationDTO userRegistrationDTO) {
        Optional<Role> role = roleRepository.findByRoleName(RolesEnum.USER.name());
        if (!role.isPresent()) {
            return null; // Role not found, registration cannot proceed
        }

        User user = new User();
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setRoles(Set.of(role.get()));
        user.setUserProfile(null); // Assuming user profile is not set during registration
        return userRepository.save(user);
    }

    /**
     * Creates a new administrator user based on the provided input data.
     *
     * @param input the data transfer object containing administrator user details
     * @return the newly created administrator User object, or null if not implemented
     */
    @Override
    public User createAdministrator(UserRegistrationDTO input) {
        return null; // Not yet implemented
    }
}
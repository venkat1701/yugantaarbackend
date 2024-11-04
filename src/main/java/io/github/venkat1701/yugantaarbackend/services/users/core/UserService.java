package io.github.venkat1701.yugantaarbackend.services.users.core;

import io.github.venkat1701.yugantaarbackend.dto.users.UserRegistrationDTO;
import io.github.venkat1701.yugantaarbackend.models.users.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

/**
 * Interface for user-related operations in the application.
 * <p>
 * This interface provides methods to handle user registration, retrieval, and management,
 * as well as integration with Spring Security for user authentication.
 * </p>
 *
 * <p>
 * Author: Venkat
 * </p>
 */
public interface UserService {

    /**
     * Loads a user by their username for authentication.
     *
     * @param username the username of the user to be loaded
     * @return a UserDetails object containing the user's information
     * @throws UsernameNotFoundException if the user with the given username cannot be found
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * Retrieves the authorities granted to the current user.
     *
     * @return a collection of granted authorities for the current user
     */
    Collection<? extends GrantedAuthority> getAuthorities();

    /**
     * Finds the user profile associated with the provided JWT.
     *
     * @param jwt the JSON Web Token used to identify the user
     * @return the User object corresponding to the JWT
     * @throws Exception if an error occurs while retrieving the user
     */
    User findUserProfileByJwt(String jwt) throws Exception;

    /**
     * Registers a new user based on the provided registration data.
     *
     * @param userRegistrationDTO the data transfer object containing user registration details
     * @return the newly registered User object
     */
    User registerUser(UserRegistrationDTO userRegistrationDTO);

    /**
     * Creates an administrator user based on the provided input data.
     *
     * @param input the data transfer object containing administrator user details
     * @return the newly created administrator User object
     */
    User createAdministrator(UserRegistrationDTO input);
}
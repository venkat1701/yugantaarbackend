package io.github.venkat1701.yugantaarbackend.services.core.users;

import io.github.venkat1701.yugantaarbackend.dto.registrations.RegistrationDTO;
import io.github.venkat1701.yugantaarbackend.dto.users.auth.GuestSignupDTO;
import io.github.venkat1701.yugantaarbackend.models.users.User;
import io.github.venkat1701.yugantaarbackend.services.core.commons.GenericCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
public interface UserService extends GenericCrudService<User, Long> {

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
     * @param registrationDTO the data transfer object containing user registration details
     * @return the newly registered User object
     */
    User registerUser(GuestSignupDTO registrationDTO);

}
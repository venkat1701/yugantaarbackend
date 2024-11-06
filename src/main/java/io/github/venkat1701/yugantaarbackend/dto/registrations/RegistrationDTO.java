package io.github.venkat1701.yugantaarbackend.dto.registrations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) for user registration.
 * <p>
 * This class is used to encapsulate the data required for registering a new user in the system.
 * It includes fields for personal information such as username, email, and password.
 * </p>
 *
 * <p>
 * @author Venkat
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {

    /**
     * The username of the user.
     * <p>
     * This field is mandatory and cannot be left blank.
     * </p>
     */
    @NotBlank(message = "Username is required.")
    @Size(max = 255, message = "Username must be less than 255 characters.")
    private String username;

    /**
     * The email address of the user.
     * <p>
     * This field is mandatory and must be a valid email format.
     * </p>
     */
    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be a valid email address.")
    @Size(max = 255, message = "Email must be less than 255 characters.")
    private String email;

    /**
     * The password chosen by the user for account security.
     * <p>
     * This field is mandatory and should meet security requirements (e.g., minimum length, complexity).
     * </p>
     */
    @NotBlank(message = "Password is required.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password;
}
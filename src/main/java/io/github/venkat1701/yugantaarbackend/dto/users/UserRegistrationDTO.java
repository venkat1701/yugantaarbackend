package io.github.venkat1701.yugantaarbackend.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for user registration.
 * <p>
 * This class is used to encapsulate the data required for registering a new user in the system.
 * It includes fields for personal information such as name, email, password, and contact details.
 * </p>
 *
 * <p>
 * Author: Venkat
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {

    /**
     * The first name of the user.
     * <p>
     * This field is mandatory and cannot be left blank.
     * </p>
     */
    @NotBlank(message = "First name is required.")
    private String firstName;

    /**
     * The last name of the user.
     * <p>
     * This field is mandatory and cannot be left blank.
     * </p>
     */
    @NotBlank(message = "Last name is required.")
    private String lastName;

    /**
     * The email address of the user.
     * <p>
     * This field is mandatory and must be a valid email format.
     * </p>
     */
    @NotBlank(message = "Email is required.")
    private String email;

    /**
     * The password chosen by the user for account security.
     * <p>
     * This field is mandatory and should meet security requirements (e.g., minimum length, complexity).
     * </p>
     */
    @NotBlank(message = "Password is required.")
    private String password;

    /**
     * The phone number of the user.
     * <p>
     * This field is optional and can be used for account recovery and communication.
     * </p>
     */
    private String phone;

    /**
     * The gender of the user.
     * <p>
     * This field is optional and can be used for demographic information.
     * </p>
     */
    private String gender;
}

package io.github.venkat1701.yugantaarbackend.dto.users.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for guest user signup.
 * <p>
 * This class is used to encapsulate the data required for registering a new guest user in the system.
 * It includes fields for personal information such as username, email, password, first name, last name, phone number, address, date of birth, profile picture URI, and bio.
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
public class GuestSignupDTO {

    /**
     * The username of the guest user.
     * <p>
     * This field is mandatory and cannot be left blank. The username must be less than 255 characters.
     * </p>
     */
    @NotBlank(message = "Username is required.")
    @Size(max = 255, message = "Username must be less than 255 characters.")
    private String username;

    /**
     * The email address of the guest user.
     * <p>
     * This field is mandatory and must be a valid email format. The email must be less than 255 characters.
     * </p>
     */
    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be a valid email address.")
    @Size(max = 255, message = "Email must be less than 255 characters.")
    private String email;

    /**
     * The password chosen by the guest user for account security.
     * <p>
     * This field is mandatory and should meet security requirements (e.g., minimum length, complexity).
     * </p>
     */
    @NotBlank(message = "Password is required.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password;

    /**
     * The first name of the guest user.
     * <p>
     * This field is mandatory and cannot be left blank. The first name must be less than 255 characters.
     * </p>
     */
    @NotBlank(message = "First name is required.")
    @Size(max = 255, message = "First name must be less than 255 characters.")
    private String firstName;

    /**
     * The last name of the guest user.
     * <p>
     * This field is mandatory and cannot be left blank. The last name must be less than 255 characters.
     * </p>
     */
    @NotBlank(message = "Last name is required.")
    @Size(max = 255, message = "Last name must be less than 255 characters.")
    private String lastName;

    /**
     * The phone number of the guest user.
     * <p>
     * This field is optional and can be used for account recovery and communication. The phone number must be less than 255 characters.
     * </p>
     */
    @Size(max = 255, message = "Phone number must be less than 255 characters.")
    private String phoneNumber;

    /**
     * The address of the guest user.
     * <p>
     * This field is optional and can be used for billing and shipping purposes.
     * </p>
     */
    private String address;

    /**
     * The date of birth of the guest user.
     * <p>
     * This field is mandatory and must be in the past.
     * </p>
     */
    @NotNull(message = "Date of birth is required.")
    @Past(message = "Date of birth must be in the past.")
    private LocalDateTime dateOfBirth;

    /**
     * The URI of the profile picture of the guest user.
     * <p>
     * This field is optional and can be used to display the user's profile picture.
     * </p>
     */
    private String profilePictureURI;

    /**
     * The bio of the guest user.
     * <p>
     * This field is optional and can be used to provide additional information about the user.
     * </p>
     */
    private String bio;
}
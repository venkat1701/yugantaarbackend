package io.github.venkat1701.yugantaarbackend.dto.users;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String email;
    private String username;
    private String phoneNumber;
    private String address;
    private LocalDateTime dateOfBirth;
    private String profilePictureURI;
    private String bio;
}

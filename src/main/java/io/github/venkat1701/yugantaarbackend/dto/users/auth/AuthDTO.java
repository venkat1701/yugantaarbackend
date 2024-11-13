package io.github.venkat1701.yugantaarbackend.dto.users.auth;

import io.github.venkat1701.yugantaarbackend.models.roles.Role;
import lombok.Data;

import java.util.List;

@Data
public class AuthDTO {
    private String username;
    private String token;
    private List<Role> rolesList;

}

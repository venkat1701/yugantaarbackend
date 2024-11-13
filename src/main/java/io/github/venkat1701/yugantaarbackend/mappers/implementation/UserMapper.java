package io.github.venkat1701.yugantaarbackend.mappers.implementation;

import io.github.venkat1701.yugantaarbackend.dto.users.UserDTO;
import io.github.venkat1701.yugantaarbackend.mappers.core.GenericMapper;
import io.github.venkat1701.yugantaarbackend.models.users.User;
import io.github.venkat1701.yugantaarbackend.models.users.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements GenericMapper<UserDTO, User> {


    @Override
    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setBio(user.getUserProfile().getBio());
        dto.setAddress(user.getUserProfile().getAddress());
        dto.setDateOfBirth(user.getUserProfile().getDateOfBirth());
        dto.setProfilePictureURI(user.getUserProfile().getProfilePictureURI());
        return dto;
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        var profile = new UserProfile();
        profile.setBio(userDTO.getBio());
        profile.setAddress(userDTO.getAddress());
        profile.setDateOfBirth(userDTO.getDateOfBirth());
        profile.setProfilePictureURI(userDTO.getProfilePictureURI());
        user.setUserProfile(profile);
        return user;
    }
}

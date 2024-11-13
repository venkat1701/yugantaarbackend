package io.github.venkat1701.yugantaarbackend.users;

import io.github.venkat1701.yugantaarbackend.controllers.implementation.users.UserControllerImplementation;
import io.github.venkat1701.yugantaarbackend.dto.users.UserDTO;
import io.github.venkat1701.yugantaarbackend.dto.users.auth.GuestSignupDTO;
import io.github.venkat1701.yugantaarbackend.models.users.User;
import io.github.venkat1701.yugantaarbackend.services.core.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(UserControllerImplementation.class)
class UserControllerImplementationTest {

    @Autowired
    private UserControllerImplementation userController;

    @MockBean
    private UserService userService;

    private User mockUser;
    private GuestSignupDTO mockSignupDTO;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");

        mockSignupDTO = new GuestSignupDTO();
        mockSignupDTO.setEmail("test@example.com");
        mockSignupDTO.setPassword("password");
    }

    @Test
    void givenUserServiceRegistersUser_whenCreateUser_thenReturnCreatedUser() {
        // Given
        when(userService.registerUser(any(GuestSignupDTO.class))).thenReturn(mockSignupDTO);

        // When
        ResponseEntity<UserDTO> response = userController.create(mockSignupDTO);

        // Then
        assertAll(
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode()),
                () -> assertEquals(mockUser, response.getBody())
        );
    }

    @Test
    void givenUserServiceReturnsUsers_whenGetAllUsers_thenReturnUsers() {
        // Given
        List<User> users = List.of(mockUser);
        when(userService.getAll()).thenReturn(users);

        // When
        ResponseEntity<List<User>> response = userController.getAll();

        // Then
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(users, response.getBody())
        );
    }

    @Test
    void givenUserServiceFindsUserById_whenGetUserById_thenReturnUser() {
        // Given
        when(userService.findById(1L)).thenReturn(Optional.of(mockUser));

        // When
        ResponseEntity<User> response = userController.getById(1L);

        // Then
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(mockUser, response.getBody())
        );
    }

    @Test
    void givenUserServiceDoesNotFindUserById_whenGetUserById_thenThrowNoSuchElementException() {
        // Given
        when(userService.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> userController.getById(1L));
    }

    @Test
    void givenUserServiceFindsUser_whenUpdateUser_thenReturnUpdatedUser() {
        // Given
        when(userService.findById(1L)).thenReturn(Optional.of(mockUser));
        when(userService.update(1L, mockUser)).thenReturn(Optional.of(mockUser));

        // When
        ResponseEntity<User> response = userController.update(1L, mockUser);

        // Then
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(mockUser, response.getBody())
        );
    }

    @Test
    void givenUserServiceDoesNotFindUser_whenUpdateUser_thenReturnNotFound() {
        // Given
        when(userService.findById(1L)).thenReturn(Optional.empty());

        // When
        ResponseEntity<User> response = userController.update(1L, mockUser);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void givenUserServiceDeletesUser_whenDeleteUser_thenReturnNoContent() {
        // Given
        when(userService.delete(1L)).thenReturn(true);

        // When
        ResponseEntity<Void> response = userController.delete(1L);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void givenUserServiceDoesNotDeleteUser_whenDeleteUser_thenReturnNotFound() {
        // Given
        when(userService.delete(1L)).thenReturn(false);

        // When
        ResponseEntity<Void> response = userController.delete(1L);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void givenUserServiceSearchesWithPagination_whenSearchUsers_thenReturnPaginatedUsers() {
        // Given
        Page<User> userPage = new PageImpl<>(List.of(mockUser));
        when(userService.search(any(PageRequest.class))).thenReturn(userPage);

        // When
        ResponseEntity<Page<User>> response = userController.search(0, 10, "id,asc");

        // Then
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertTrue(response.getBody().hasContent()),
                () -> assertEquals(1, response.getBody().getTotalElements())
        );
    }
}

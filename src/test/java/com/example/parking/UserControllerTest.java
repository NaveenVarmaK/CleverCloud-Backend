package com.example.parking;


import com.example.parking.models.User;
import com.example.parking.repositories.UserRepository;
import com.example.parking.controller.UserController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Test
    public void testGetUsers() {
        // Create a user
        User user = new User();
        user.setId(1L);
        user.setFirstName("Stephen");
        user.setLastName("Strange");

        // Mock the behavior of userRepository.save()
        when(userRepository.save(user)).thenReturn(user);

        // Mock the behavior of userRepository.findAll()
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        // Call the method under test (getUsers)
        ResponseEntity<List<User>> responseEntity = userController.getUsers();

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(user, responseEntity.getBody().get(0));

//         Additional assertions (uncomment if needed)
//         User userFromDb = userRepository.getReferenceById(user.getId());
//         assertEquals(userFromDb.getFirstName(), "Tony");
    }
    @Test
    public void testGetUserByIdFound() {
        long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<User> responseEntity = userController.getUser(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    public void testGetUserByIdNotFound() {
        long userId = 2L; // Change the user ID to 2L
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseEntity<User> responseEntity = userController.getUser(userId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    public void testDeleteUserFound() {
        long userId = 2L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));

        ResponseEntity<HttpStatus> responseEntity = userController.deleteUser(userId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(userRepository, times(1)).deleteById(userId);
    }
    @Test
    public void testUpdateUserFound() {
        long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setFirstName("UpdatedFirstName");

        ResponseEntity<User> responseEntity = userController.updateUser(userId, updatedUser);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedUser.getFirstName(), responseEntity.getBody().getFirstName());
        verify(userRepository, times(1)).save(existingUser);
    }
    @Test
    public void testUpdateUserNotFound() {
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        User updatedUser = new User();
        ResponseEntity<User> responseEntity = userController.updateUser(userId, updatedUser);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(userRepository, never()).save(any(User.class));
    }
}


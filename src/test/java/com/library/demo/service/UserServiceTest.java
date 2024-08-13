package com.library.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.library.entity.UserEntity;
import com.library.model.User;
import com.library.repository.UserRepository;
import com.library.service.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserById_UserExists() {
        // Arrange
        UserEntity mockUser = new UserEntity();
        mockUser.setId(1);
        mockUser.setName("John Doe");
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));

        // Act
        Optional<UserEntity> result = userService.getById(1);

        // Assert
        assertTrue(result.isPresent());
        UserEntity user = result.get();
        assertEquals(1, user.getId());
        assertEquals("John Doe", user.getName());
    }

    @Test
    public void testGetUserById_UserDoesNotExist() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        Optional<UserEntity> result = userService.getById(1);

        // Assert
        assertTrue(result.isEmpty());

    }

    @Test
    public void testCreateUser() {
        // Arrange
        UserEntity mockUser = new UserEntity();
        mockUser.setId(1);
        mockUser.setName("John Doe");
        when(userRepository.save(any(UserEntity.class))).thenReturn(mockUser);

        // Act
        UserEntity result = userService.create(mockUser);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("John Doe", result.getName());
    }

    @Test
    public void testSaveUser() {
        // Arrange
        User user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setRole("Admin");
        user.setPassword("password");

        UserEntity mockUserEntity = new UserEntity();
        mockUserEntity.setId(1);
        mockUserEntity.setName("John Doe");
        mockUserEntity.setRole("Admin");
        mockUserEntity.setPassword("password");

        when(userRepository.save(any(UserEntity.class))).thenReturn(mockUserEntity);

        // Act
        userService.saveUser(user);

        // Assert
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
public void testAuthenticate_Successful() {
    // Arrange
    UserEntity mockUser = new UserEntity();
    mockUser.setId(1);
    mockUser.setPassword("password");
    when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));

    // Act
    Optional<UserEntity> result = userService.authenticate(1, "password");

    // Assert
    assertTrue(result.isPresent());
    UserEntity user = result.get();
    assertEquals(1, user.getId());
}


    @Test
    public void testAuthenticate_Unsuccessful() {
        // Arrange
        UserEntity mockUser = new UserEntity();
        mockUser.setId(1);
        mockUser.setPassword("password");
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));

        // Act
        Optional<UserEntity> result = userService.authenticate(1, "wrongpassword");

        // Assert
        assertNull(result);
    }

    @Test
    public void testAuthenticate_UserDoesNotExist() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        Optional<UserEntity> result = userService.authenticate(1, "password");

        // Assert
        assertNull(result);
    }
}

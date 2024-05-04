package com.testtask.ClearSolution;

import com.testtask.ClearSolution.exceptions.InvalidPropertiesException;
import com.testtask.ClearSolution.exceptions.UserAlreadyExistsException;
import com.testtask.ClearSolution.exceptions.UserWasntFoundException;
import com.testtask.ClearSolution.model.User;
import com.testtask.ClearSolution.repository.UserRepository;
import com.testtask.ClearSolution.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBirthDate(LocalDate.of(1990, 1, 1));

        when(userRepository.findAll()).thenReturn(Arrays.asList());
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.create(user);

        assertNotNull(savedUser);
        assertEquals("John", savedUser.getFirstName());
        assertEquals("Doe", savedUser.getLastName());
        verify(userRepository, times(1)).save(user);
    }


    @Test
    public void testReadByEmail() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findUsersByEmail(email)).thenReturn(user);

        User foundUser = userService.readByEmail(email);

        assertEquals(email, foundUser.getEmail());
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBirthDate(LocalDate.of(1990, 1, 1));

        when(userRepository.findUsersByEmail(user.getEmail())).thenReturn(user);

        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.update(user);

        assertNotNull(updatedUser);
        assertEquals("John", updatedUser.getFirstName());
        assertEquals("Doe", updatedUser.getLastName());

        verify(userRepository, times(1)).save(user);
    }


    @Test
    public void testDeleteUser() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findUsersByEmail(email)).thenReturn(user);

        userService.delete(email);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testGetAllUsersByBirthDate() {
        LocalDate fromDate = LocalDate.of(1990, 1, 1);
        LocalDate toDate = LocalDate.of(2000, 1, 1);

        userService.getAllUsersByBirthDate(fromDate, toDate);

        verify(userRepository, times(1)).findByDateOfBirthBetween(fromDate, toDate);
    }

    @Test
    public void testCreateUserWithEmptyFields() {
        User user = new User();
        user.setEmail("");
        user.setFirstName("");
        user.setLastName("");
        user.setBirthDate(null);

        assertThrows(InvalidPropertiesException.class, () -> userService.create(user));

        verify(userRepository, never()).save(any());
    }

    @Test
    public void testCreateUserWithExistingEmail() {
        User user = new User();
        user.setEmail("existing@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBirthDate(LocalDate.of(1990, 1, 1));

        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        assertThrows(UserAlreadyExistsException.class, () -> userService.create(user));

        verify(userRepository, never()).save(any());
    }

    @Test
    public void testUpdateUserWithEmptyFields() {
        User user = new User();
        user.setEmail("");
        user.setFirstName("");
        user.setLastName("");
        user.setBirthDate(null);

        assertThrows(InvalidPropertiesException.class, () -> userService.update(user));
        verify(userRepository, never()).save(any());
    }

    @Test
    public void testReadByEmailWithNonExistingEmail() {
        String nonExistingEmail = "nonexisting@example.com";
        when(userRepository.findUsersByEmail(nonExistingEmail)).thenReturn(null);

        assertThrows(UserWasntFoundException.class, () -> userService.readByEmail(nonExistingEmail));
    }

    @Test
    public void testUpdateUserWithNonExistingEmail() {
        User user = new User();
        user.setEmail("nonexisting@example.com");

        when(userRepository.findUsersByEmail(user.getEmail())).thenReturn(null);
        when(userRepository.save(user)).thenReturn(user);

        verify(userRepository, never()).save(any());
    }

    @Test
    public void testDeleteUserWithNonExistingEmail() {
        String nonExistingEmail = "nonexisting@example.com";

        when(userRepository.findUsersByEmail(nonExistingEmail)).thenReturn(null);
        assertThrows(UserWasntFoundException.class, () -> userService.delete(nonExistingEmail));

        verify(userRepository, never()).delete(any());
    }

    @Test
    public void testGetAllUsersByBirthDateWithInvalidDates() {
        LocalDate fromDate = LocalDate.of(2000, 1, 1);
        LocalDate toDate = LocalDate.of(1990, 1, 1);

        assertThrows(InvalidPropertiesException.class, () -> userService.getAllUsersByBirthDate(fromDate, toDate));

        verify(userRepository, never()).findByDateOfBirthBetween(any(), any());
    }
}

package com.testtask.ClearSolution.service.impl;

import com.testtask.ClearSolution.exceptions.InvalidPropertiesException;
import com.testtask.ClearSolution.exceptions.UserAlreadyExistsException;
import com.testtask.ClearSolution.exceptions.UserIsNotOldEnoughException;
import com.testtask.ClearSolution.exceptions.UserWasntFoundException;
import com.testtask.ClearSolution.model.User;
import com.testtask.ClearSolution.repository.UserRepository;
import com.testtask.ClearSolution.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${minimal.age}")
    private int minimalAge;
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        log.info("Creating user: {}", user);
        List<User> users = userRepository.findAll();
        if (user.getEmail().isEmpty() || user.getFirstName().isEmpty() || user.getLastName().isEmpty()
                || user.getBirthDate() == null) {
            log.error("Some user fields might be empty");
            throw new InvalidPropertiesException("some user fields might be empty");
        }
        if(!checkAge(user.getBirthDate())){
            throw new UserIsNotOldEnoughException();
        }
        if (users.contains(user)) {
            log.error("User already exists: {}", user.getEmail());
            throw new UserAlreadyExistsException("User is already exist!");
        }
        log.info("User created.");
        return userRepository.save(user);
    }

    @Override
    public User readByEmail(String email) {
        log.info("Reading user by email: {}", email);
        User requestedUser = userRepository.findUsersByEmail(email);
        if (requestedUser != null) {
            log.info("User found: {}", requestedUser);
            return requestedUser;
        } else {
            log.error("User not found with email: {}", email);
            throw new UserWasntFoundException("cant find user by this email: " + email);
        }
    }

    @Override
    public User update(User user) {
        log.info("Updating user: {}", user);
        if (user.getEmail().isEmpty() || user.getFirstName().isEmpty() || user.getLastName().isEmpty() || user.getBirthDate() == null) {
            log.error("Some user fields are empty");
            throw new InvalidPropertiesException("Some user fields might be empty");
        }

        User existingUser = readByEmail(user.getEmail());

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setBirthDate(user.getBirthDate());

        if (user.getAddress() != null && user.getPhoneNumber() != null) {
            existingUser.setAddress(user.getAddress());
            existingUser.setPhoneNumber(user.getPhoneNumber());
        }

        User updatedUser = userRepository.save(existingUser);
        log.info("User updated: {}", updatedUser);
        return updatedUser;
    }

    @Override
    public void delete(String email) {
        log.info("Deleting user by email: {}", email);
        userRepository.delete(readByEmail(email));
        log.info("User deleted successfully");
    }

    @Override
    public List<User> getAllUsersByBirthDate(LocalDate fromDate, LocalDate toDate) {
        log.info("Fetching users by birth date. From: {}, To: {}", fromDate, toDate);
        if (fromDate == null && toDate == null) {
            log.error("fromDate or toDate wasn't provided the right way");
            throw new InvalidPropertiesException("fromDate or toDate wasn't provided the right way.");
        }
        if (fromDate.isAfter(toDate)) {
            log.error("fromDate cannot be after toDate");
            throw new InvalidPropertiesException("fromDate cannot be after toDate.");
        }
        List<User> users = userRepository.findByDateOfBirthBetween(fromDate, toDate);
        log.info("Users fetched successfully: {}", users);
        return users;
    }

    private boolean checkAge(LocalDate birthDate) {
        int years = Period.between(birthDate, LocalDate.now()).getYears();
        if (years < minimalAge) {
            return false;
        }
        return true;
    }
}

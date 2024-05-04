package com.testtask.ClearSolution.controller;

import com.testtask.ClearSolution.model.User;
import com.testtask.ClearSolution.service.UserService;
import com.testtask.ClearSolution.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("Post Mapping create User url: /users/ user_email : " + user.getEmail());
        User createdUser = userService.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        log.info("Delete Mapping delete User url: /users/ " + email);
        userService.delete(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        log.info("Put Mapping update User by email : " + user.getEmail());
        User updatedUser = userService.update(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(name = "fromDate") LocalDate fromDate, @RequestParam(name = "toDate") LocalDate toDate) {
        log.info("Get Mapping get Users from" + fromDate + " to " + toDate);
        List<User> users = userService.getAllUsersByBirthDate(fromDate, toDate);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}

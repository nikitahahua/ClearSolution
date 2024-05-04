package com.testtask.ClearSolution.service;

import com.testtask.ClearSolution.model.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface UserService {
    User create(User user);
    User readByEmail(String email);
    User update(User user);
    void delete(String email);
    List<User> getAllUsersByBirthDate(LocalDate fromDate, LocalDate toDate);
}

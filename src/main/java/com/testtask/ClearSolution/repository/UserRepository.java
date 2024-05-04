package com.testtask.ClearSolution.repository;

import com.testtask.ClearSolution.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT user FROM User user WHERE user.birthDate between :fromDate AND :toDate")
    List<User> findByDateOfBirthBetween(LocalDate fromDate, LocalDate toDate);

    User findUsersByEmail(String email);
}

package com.testtask.ClearSolution;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.testtask.ClearSolution.controller.UserController;
import com.testtask.ClearSolution.model.User;
import com.testtask.ClearSolution.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBirthDate(LocalDate.of(1990, 1, 1));

        given(userService.create(any(User.class))).willReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.birthDate", is(user.getBirthDate().toString())));

        verify(userService, times(1)).create(any(User.class));
    }

    @Test
    public void testDeleteUser() throws Exception {
        String email = "test@example.com";

        mockMvc.perform(delete("/users/{email}", email))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).delete(email);
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBirthDate(LocalDate.of(1990, 1, 1));

        given(userService.update(any(User.class))).willReturn(user);

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.birthDate", is(user.getBirthDate().toString())));

        verify(userService, times(1)).update(any(User.class));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        LocalDate fromDate = LocalDate.of(1990, 1, 1);
        LocalDate toDate = LocalDate.of(2000, 1, 1);

        List<User> users = new ArrayList<>();
        users.add(new User("test1@example.com", "John", "Doe", LocalDate.of(1995, 1, 1)));
        users.add(new User("test2@example.com", "Jane", "Doe", LocalDate.of(1998, 1, 1)));

        given(userService.getAllUsersByBirthDate(fromDate, toDate)).willReturn(users);

        mockMvc.perform(get("/users")
                        .param("fromDate", fromDate.toString())
                        .param("toDate", toDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].email", is("test1@example.com")))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Doe")))
                .andExpect(jsonPath("$[0].birthDate", is("1995-01-01")))
                .andExpect(jsonPath("$[1].email", is("test2@example.com")))
                .andExpect(jsonPath("$[1].firstName", is("Jane")))
                .andExpect(jsonPath("$[1].lastName", is("Doe")))
                .andExpect(jsonPath("$[1].birthDate", is("1998-01-01")));

        verify(userService, times(1)).getAllUsersByBirthDate(fromDate, toDate);
    }

    private static String asJsonString(final User user) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

            ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            return ow.writeValueAsString(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

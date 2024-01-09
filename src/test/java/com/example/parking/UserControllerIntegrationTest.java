package com.example.parking;

import com.example.parking.models.User;
import com.example.parking.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(userRepository.findAll().size()));
    }

    @Test
    public void testCreateUser() throws Exception {
        String userJson = "{\"firstName\": \"Stephen\", \"lastName\": \"Hawking\", \"carNumber\": \"CU2069\", \"startTime\":\"12:00\", \"endTime\":\"15:00\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.firstName").value("Stephen"))
                .andExpect(jsonPath("$.lastName").value("Hawking"))
                .andExpect(jsonPath("$.carNumber").value("CU2069"))
                .andExpect(jsonPath("$.startTime").value("12:00"))
                .andExpect(jsonPath("$.endTime").value("15:00"));
    }
    @Test
    public void testGetUserById() throws Exception {
        // Assuming there is a user with ID 1 in the database
        long userId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userId));
    }

    @Test
    public void testGetUserByIdNotFound() throws Exception {
        // Assuming there is no user with ID 100 in the database
        long userId = 100L;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", userId))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testUpdateUserNotFound() throws Exception {
        // Assuming there is no user with ID 100 in the database
        long userId = 100L;

        // Assuming you want to update the user's first name
        String updatedFirstName = "UpdatedFirstName";
        User updatedUser = new User();
        updatedUser.setFirstName(updatedFirstName);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{id}", userId)
                        .content(objectMapper.writeValueAsString(updatedUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testDeleteUserNotFound() throws Exception {
        // Assuming there is no user with ID 100 in the database
        long userId = 100L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{id}", userId))
                .andExpect(status().isNotFound());
    }

}

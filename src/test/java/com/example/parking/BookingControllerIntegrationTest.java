package com.example.parking;

import com.example.parking.models.Booking;
import com.example.parking.models.Slot;
import com.example.parking.models.User;
import com.example.parking.repositories.BookingRepository;
import com.example.parking.repositories.SlotRepository;
import com.example.parking.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SlotRepository slotRepository;

    @Test
    public void testGetBookings() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/bookings"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetBookingById() throws Exception {
        // Assuming there is a booking with ID 1 in the database
        long bookingId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/bookings/{id}", bookingId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetBookingByIdNotFound() throws Exception {
        // Assuming there is no booking with ID 100 in the database
        long bookingId = 100L;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/bookings/{id}", bookingId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testCreateBooking() throws Exception {
        // Assuming there are users and slots in the database with IDs 1 and 2, respectively
        long userId = 1L;
        long slotId = 2L;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bookings")
                        .param("userId", String.valueOf(userId))
                        .param("slotId", String.valueOf(slotId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateBookingUserNotFound() throws Exception {
        // Assuming there is no user with ID 100 in the database
        long userId = 100L;
        long slotId = 2L;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bookings")
                        .param("userId", String.valueOf(userId))
                        .param("slotId", String.valueOf(slotId)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testCreateBookingSlotNotFound() throws Exception {
        // Assuming there is no slot with ID 200 in the database
        long userId = 1L;
        long slotId = 200L;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bookings")
                        .param("userId", String.valueOf(userId))
                        .param("slotId", String.valueOf(slotId)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testGetBookingsByUser() throws Exception {
        // Assuming there is a user with ID 1 in the database
        long userId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/bookings/user/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetBookingsByUserNotFound() throws Exception {
        // Assuming there is no user with ID 100 in the database
        long userId = 100L;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/bookings/user/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteBooking() throws Exception {
        // Assuming there is a booking with ID 1 in the database
        long bookingId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/bookings/{id}", bookingId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteBookingNotFound() throws Exception {
        // Assuming there is no booking with ID 100 in the database
        long bookingId = 100L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/bookings/{id}", bookingId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

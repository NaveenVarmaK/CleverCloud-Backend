package com.example.parking;

import com.example.parking.controller.BookingController;
import com.example.parking.models.Booking;
import com.example.parking.models.Slot;
import com.example.parking.models.User;
import com.example.parking.repositories.BookingRepository;
import com.example.parking.repositories.SlotRepository;
import com.example.parking.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingControllerTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SlotRepository slotRepository;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBookings() {
        // Mocking data
        List<Booking> bookings = Arrays.asList(new Booking(), new Booking());
        when(bookingRepository.findAll()).thenReturn(bookings);

        // Testing the controller method
        ResponseEntity<List<Booking>> responseEntity = bookingController.getBookings();

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(bookings, responseEntity.getBody());
    }

    @Test
    void testGetBookingById() {
        // Mocking data
        long bookingId = 1L;
        Booking booking = new Booking();
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        // Testing the controller method
        ResponseEntity<Booking> responseEntity = bookingController.getBooking(bookingId);

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(booking, responseEntity.getBody());
    }

    @Test
    void testGetBookingByIdNotFound() {
        // Mocking data
        long bookingId = 1L;
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        // Testing the controller method
        ResponseEntity<Booking> responseEntity = bookingController.getBooking(bookingId);

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testCreateBooking() {
        // Mocking data
        long userId = 1L;
        long slotId = 2L;
        User user = new User();
        Slot slot = new Slot();
        Booking booking = new Booking();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(slotRepository.findById(slotId)).thenReturn(Optional.of(slot));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        // Testing the controller method
        ResponseEntity<Booking> responseEntity = bookingController.createBooking(userId, slotId);

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(booking, responseEntity.getBody());
    }

    @Test
    void testCreateBookingUserNotFound() {
        // Mocking data
        long userId = 1L;
        long slotId = 2L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Testing the controller method
        ResponseEntity<Booking> responseEntity = bookingController.createBooking(userId, slotId);

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testCreateBookingSlotNotFound() {
        // Mocking data
        long userId = 1L;
        long slotId = 2L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(slotRepository.findById(slotId)).thenReturn(Optional.empty());

        // Testing the controller method
        ResponseEntity<Booking> responseEntity = bookingController.createBooking(userId, slotId);

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testGetBookingsByUser() {
        // Mocking data
        long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        List<Booking> bookings = Arrays.asList(new Booking(), new Booking());
        when(bookingRepository.findByUser(user)).thenReturn(bookings);

        // Testing the controller method
        ResponseEntity<List<Booking>> responseEntity = bookingController.getBookingByUser(userId);

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(bookings, responseEntity.getBody());
    }

    @Test
    void testGetBookingsByUserNotFound() {
        // Mocking data
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Testing the controller method
        ResponseEntity<List<Booking>> responseEntity = bookingController.getBookingByUser(userId);

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testDeleteBooking() {
        // Mocking data
        long bookingId = 1L;
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(new Booking()));

        // Testing the controller method
        ResponseEntity<HttpStatus> responseEntity = bookingController.deleteBooking(bookingId);

        // Assertions
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteBookingNotFound() {
        // Mocking data
        long bookingId = 1L;
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        // Testing the controller method
        ResponseEntity<HttpStatus> responseEntity = bookingController.deleteBooking(bookingId);

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}

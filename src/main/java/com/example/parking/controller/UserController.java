package com.example.parking.controller;

import com.example.parking.models.Booking;
import com.example.parking.models.User;
import com.example.parking.repositories.BookingRepository;
import com.example.parking.repositories.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "User", description = "User management APIs")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@EntityScan(basePackageClasses = {User.class})
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserRepository repository;

    @Autowired
    public BookingRepository bookingRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        logger.info("Get users request has been made");
        List<User> users = this.repository.findAll();
        logger.debug("Number of users retrieved: " + users.size());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = this.repository.save(user);
        logger.info("User created successfully. User ID: " + savedUser.getId());
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        Optional<User> userOptional = this.repository.findById(id);

        if (userOptional.isEmpty()) {
            logger.warn("Trying to delete non-existing user with id: " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            User user = userOptional.get();
            List<Booking> bookings = bookingRepository.findByUser(user);

            this.bookingRepository.deleteAll(bookings);
            this.repository.deleteById(id);
            logger.info("User deleted successfully. User ID: " + id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        if (this.repository.findById(id).isEmpty()) {
            logger.info("Cannot find the user with id: " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            User user = this.repository.findById(id).get();
            logger.info("User retrieved successfully. User ID: " + user.getId());
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }


    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        if (this.repository.findById(id).isEmpty()) {
            logger.error("Cannot find the user with id: " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            User existingUser = this.repository.findById(id).get();
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setCarNumber(user.getCarNumber());
            existingUser.setStartTime(user.getStartTime());
            existingUser.setEndTime(user.getEndTime());
            this.repository.save(existingUser);
            logger.info("User updated successfully. User ID: " + existingUser.getId());
            return new ResponseEntity<>(existingUser, HttpStatus.OK);
        }
    }

}

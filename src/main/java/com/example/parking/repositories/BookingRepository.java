package com.example.parking.repositories;

import com.example.parking.models.Booking;
import com.example.parking.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    public List<Booking> findByUser(User user);

}

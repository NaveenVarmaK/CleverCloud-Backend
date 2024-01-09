package com.example.parking.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "boooking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue
    private long id;


     // a foreign key to the user table
    @ManyToOne(fetch = FetchType.EAGER)  // Assuming you want a lazy fetch type
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)  // Assuming you want a lazy fetch type
    @JoinColumn(name = "slot_id", nullable = false)
    private Slot slot;

}

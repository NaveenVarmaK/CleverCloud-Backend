package com.example.parking.controller;

import com.example.parking.models.Slot;
import com.example.parking.repositories.SlotRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Slot", description = "Slot management APIs")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@EntityScan(basePackageClasses = {Slot.class})
public class SlotController {

    private static final Logger logger = LoggerFactory.getLogger(SlotController.class);

    @Autowired
    public SlotRepository repository;

    @GetMapping("/slots")
    public ResponseEntity<List<Slot>> getSlots() {
        logger.info("Get slots request has been made");
        List<Slot> slots = this.repository.findAll();
        logger.debug("Number of slots retrieved: " + slots.size());
        return new ResponseEntity<>(slots, HttpStatus.OK);
    }

    @GetMapping("/slots/{id}")
    public ResponseEntity<Slot> getSlot(@PathVariable("id") long id) {
        if (this.repository.findById(id).isEmpty()) {
            logger.debug("Cannot find the slot with id: " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Slot slot = this.repository.findById(id).get();
            logger.info("Slot retrieved successfully. Slot ID: " + slot.getId());
            return new ResponseEntity<>(slot, HttpStatus.OK);
        }
    }

    @PostMapping("/slots")
    public ResponseEntity<Slot> createSlot(@RequestBody Slot slot) {
        Slot savedSlot = this.repository.save(slot);
        logger.info("Slot created successfully. Slot ID: " + savedSlot.getId());
        return new ResponseEntity<>(savedSlot, HttpStatus.CREATED);
    }

    @PutMapping("/slots/{id}")
    public ResponseEntity<Slot> updateSlot(@PathVariable("id") long id, @RequestBody Slot slot) {
        if (this.repository.findById(id).isEmpty()) {
            logger.error("Cannot find the slot with id: " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Slot existingSlot = this.repository.findById(id).get();
            existingSlot.setArea(slot.getArea());
            existingSlot.setCity(slot.getCity());
            existingSlot.setIsBusy(slot.getIsBusy());
            existingSlot.setCountry(slot.getCountry());
            existingSlot.setStatus(slot.getStatus());
            existingSlot.setStreet(slot.getStreet());
            this.repository.save(existingSlot);
            logger.info("Slot updated successfully. Slot ID: " + existingSlot.getId());
            return new ResponseEntity<>(existingSlot, HttpStatus.OK);
        }
    }

    @DeleteMapping("/slots/{id}")
    public ResponseEntity<HttpStatus> deleteSlot(@PathVariable("id") long id) {
        if (this.repository.findById(id).isEmpty()) {
            logger.warn("Trying to delete non-existing slot with id: " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            this.repository.deleteById(id);
            logger.info("Slot deleted successfully. Slot ID: " + id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}

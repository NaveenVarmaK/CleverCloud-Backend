package com.example.parking;

import com.example.parking.controller.SlotController;
import com.example.parking.models.Slot;
import com.example.parking.repositories.SlotRepository;
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
public class SlotControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetSlots() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/slots"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetSlotById() throws Exception {
        // Assuming there is a slot with ID 1 in the database
        long slotId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/slots/{id}", slotId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(slotId));
    }

    @Test
    public void testGetSlotByIdNotFound() throws Exception {
        // Assuming there is no slot with ID 100 in the database
        long slotId = 100L;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/slots/{id}", slotId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateSlot() throws Exception {
        Slot newSlot = new Slot();
        newSlot.setArea("TestArea");
        newSlot.setCity("TestCity");
        newSlot.setStreet("TestStreet");
        newSlot.setCountry("TestCountry");
        newSlot.setStatus("TestBooking");
        newSlot.setIsBusy(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/slots")
                        .content(objectMapper.writeValueAsString(newSlot))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Test
    public void testUpdateSlot() throws Exception {
        // Assuming there is a slot with ID 1 in the database
        long slotId = 1L;

        Slot updatedSlot = new Slot();
        updatedSlot.setArea("UpdatedArea");
        updatedSlot.setCity("UpdatedCity");
        updatedSlot.setStatus("UpdateBooking");
        updatedSlot.setStreet("UpdatedStreet");
        updatedSlot.setCountry("UpdatedCountry");
        updatedSlot.setIsBusy(false);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/slots/{id}", slotId)
                        .content(objectMapper.writeValueAsString(updatedSlot))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(slotId));
    }

    @Test
    public void testUpdateSlotNotFound() throws Exception {
        // Assuming there is no slot with ID 100 in the database
        long slotId = 100L;

        Slot updatedSlot = new Slot();
        updatedSlot.setArea("UpdatedArea");
        updatedSlot.setCity("UpdatedCity");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/slots/{id}", slotId)
                        .content(objectMapper.writeValueAsString(updatedSlot))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteSlot() throws Exception {
        // Assuming there is a slot with ID 1 in the database
        long slotId = 2L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/slots/{id}", slotId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteSlotNotFound() throws Exception {
        // Assuming there is no slot with ID 100 in the database
        long slotId = 69L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/slots/{id}", slotId))
                .andExpect(status().isNotFound());
    }
}

package com.scalable.tracking.ServiceImpl;

import com.scalable.tracking.Model.TrackingResponse;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TrackingServiceImplTest {

    private final TrackingServiceImpl trackingService = new TrackingServiceImpl();

    @Test
    void generateTrackingNumber() {
        UUID customerId = UUID.randomUUID();
        TrackingResponse response = trackingService.generateTrackingNumber("US", "CA", 1.5, null, customerId, "John Doe", "johndoe");
        assertNotNull(response.getTrackingNumber());
        assertFalse(response.getTrackingNumber().isEmpty());
    }

    @Test
    void generateTrackingId_ShouldReturnValidTrackingNumber() {
        String yearStr = "25";
        String originCountryId = "US";
        String destinationCountryId = "IN";

        String trackingId = trackingService.generateTrackingId(yearStr, originCountryId, destinationCountryId);

        assertNotNull(trackingId, "Tracking ID should not be null");
        assertEquals(16, trackingId.length(), "Tracking ID length should be 16 characters");
        assertTrue(trackingId.startsWith(yearStr + originCountryId + destinationCountryId), "Tracking ID should start with year, origin, and destination codes");
    }
}